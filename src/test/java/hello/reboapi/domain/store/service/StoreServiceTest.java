package hello.reboapi.domain.store.service;

import hello.reboapi.domain.kakao.dto.KakaoGeocodeResponse;
import hello.reboapi.domain.kakao.service.KakaoService;
import hello.reboapi.domain.store.dto.SearchStoreRequest;
import hello.reboapi.domain.store.entity.Store;
import hello.reboapi.domain.store.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @Mock
    private KakaoService kakaoService;

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreService storeService;

    private Store createTestStore() {
        return Store.builder()
                .name("위넌 스터디카페")
                .csvId("TEST001")
                .categorySmall("스터디카페")
                .latitude(new BigDecimal("37.506322"))
                .longitude(new BigDecimal("127.053834"))
                .build();
    }

    @Test
    @DisplayName("위치와 카테고리로 반경 내 매장을 검색한다")
    void searchStoreTest() {
        // given
        SearchStoreRequest request = new SearchStoreRequest("논현역", "스터디카페", 500);
        KakaoGeocodeResponse geocodeResponse = new KakaoGeocodeResponse("37.506322", "127.053834", "논현동");
        Store testStore = createTestStore();

        given(kakaoService.searchKeyword(anyString()))
            .willReturn(geocodeResponse);
        given(storeRepository.findStoresByLocationAndCategory(
            anyDouble(), anyDouble(), anyInt(), anyString()))
            .willReturn(Arrays.asList(testStore));

        // when
        List<Store> results = storeService.searchStore(request);

        // then
        assertThat(results).isNotEmpty();
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getName()).isEqualTo("위넌 스터디카페");
        
        verify(kakaoService).searchKeyword(request.getKeyword());
        verify(storeRepository).findStoresByLocationAndCategory(
            geocodeResponse.getLatitude(),
            geocodeResponse.getLongitude(),
            request.getRadius(),
            request.getCategory()
        );
    }
} 