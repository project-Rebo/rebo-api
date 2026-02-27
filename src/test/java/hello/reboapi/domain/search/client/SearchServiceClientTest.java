package hello.reboapi.domain.search.client;

import hello.reboapi.domain.search.dto.CategorySearchRequest;
import hello.reboapi.domain.search.dto.RegionResponse;
import hello.reboapi.domain.search.dto.RegionSaveRequest;
import hello.reboapi.domain.search.dto.StoreAnalysisCache;
import hello.reboapi.domain.search.dto.StoreInputResponse;
import hello.reboapi.domain.search.dto.StoreInputSaveRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SearchServiceClientTest {

    @InjectMocks
    private SearchServiceClient searchServiceClient;

    @Mock
    private RestTemplate restTemplate;

    @Test
    @DisplayName("카테고리 검색 호출 성공")
    void searchCategory_success() {
        // given
        CategorySearchRequest request = new CategorySearchRequest(
                "강남역", "음식", null, null, "치킨", 1000
        );

        StoreAnalysisCache expected = StoreAnalysisCache.builder()
                .region("강남역")
                .categoryLarge("음식")
                .totalStores(50)
                .density(15.9)
                .radius(1000)
                .build();

        given(restTemplate.postForObject(
                eq("http://localhost:8082/api/stores/category/Cache"),
                eq(request),
                eq(StoreAnalysisCache.class)
        )).willReturn(expected);

        // when
        StoreAnalysisCache result = searchServiceClient.searchCategory(request);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTotalStores()).isEqualTo(50);
        assertThat(result.getRegion()).isEqualTo("강남역");
    }

    @Test
    @DisplayName("StoreInput 저장 호출 성공")
    void saveStoreInput_success() {
        // given
        Long memberId = 1L;
        StoreInputSaveRequest request = StoreInputSaveRequest.builder()
                .location("강남역")
                .category("음식")
                .radius(1000)
                .build();

        StoreInputResponse expected = new StoreInputResponse(
                1L, memberId, "강남역", "음식", 1000,
                LocalDateTime.now(), LocalDateTime.now()
        );

        given(restTemplate.postForObject(
                eq("http://localhost:8082/api/v1/store_input/save?memberId=1"),
                eq(request),
                eq(StoreInputResponse.class)
        )).willReturn(expected);

        // when
        StoreInputResponse result = searchServiceClient.saveStoreInput(memberId, request);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getLocation()).isEqualTo("강남역");
    }

    @Test
    @DisplayName("Region 저장 호출 성공")
    void saveRegion_success() {
        // given
        Long storeInputId = 1L;
        RegionSaveRequest request = RegionSaveRequest.builder()
                .roadAddress("역삼동")
                .latitude(new BigDecimal("37.498095"))
                .longitude(new BigDecimal("127.027610"))
                .build();

        RegionResponse expected = new RegionResponse(
                1L, storeInputId, "역삼동",
                new BigDecimal("127.027610"), new BigDecimal("37.498095"),
                LocalDateTime.now(), LocalDateTime.now()
        );

        given(restTemplate.postForObject(
                eq("http://localhost:8082/api/v1/region/save?storeInputId=1"),
                eq(request),
                eq(RegionResponse.class)
        )).willReturn(expected);

        // when
        RegionResponse result = searchServiceClient.saveRegion(storeInputId, request);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getRoadAddress()).isEqualTo("역삼동");
    }
}
