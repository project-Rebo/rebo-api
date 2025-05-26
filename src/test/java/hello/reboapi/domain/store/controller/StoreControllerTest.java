package hello.reboapi.domain.store.controller;

import hello.reboapi.domain.store.entity.Store;
import hello.reboapi.domain.store.service.StoreService;
import hello.reboapi.domain.store.dto.SearchStoreRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class StoreControllerTest {

    @Mock
    private StoreService storeService;

    @InjectMocks
    private StoreController storeController;

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
    @DisplayName("올바른 검색 파라미터로 요청시 매장 목록을 반환한다")
    void searchStoreTest() {
        // given
        SearchStoreRequest request = new SearchStoreRequest("논현역", "스터디카페", 500);
        Store testStore = createTestStore();

        given(storeService.searchStore(any(SearchStoreRequest.class)))
            .willReturn(Arrays.asList(testStore));

        // when
        ResponseEntity<List<Store>> response = storeController.searchStore(request);

        // then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getName()).isEqualTo("위넌 스터디카페");
    }

    @Test
    @DisplayName("반경이 음수일 경우 예외가 발생한다")
    void searchStoreWithNegativeRadiusTest() {
        // given
        SearchStoreRequest request = new SearchStoreRequest("논현역", "스터디카페", -1);

        // when & then
        assertThat(request.getRadius()).isNegative();
    }

    @Test
    @DisplayName("키워드가 비어있을 경우 예외가 발생한다")
    void searchStoreWithEmptyKeywordTest() {
        // given
        SearchStoreRequest request = new SearchStoreRequest("", "스터디카페", 500);

        // when & then
        assertThat(request.getKeyword()).isEmpty();
    }
} 