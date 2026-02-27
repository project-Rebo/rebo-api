package hello.reboapi.domain.search.client;

import hello.reboapi.domain.search.dto.CategorySearchRequest;
import hello.reboapi.domain.search.dto.RegionSaveRequest;
import hello.reboapi.domain.search.dto.RegionResponse;
import hello.reboapi.domain.search.dto.StoreAnalysisCache;
import hello.reboapi.domain.search.dto.StoreInputResponse;
import hello.reboapi.domain.search.dto.StoreInputSaveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchServiceClient {

    private final RestTemplate restTemplate;
    private static final String SEARCH_SERVICE_URL = "http://localhost:8082";

    /**
     * 카테고리 기반 상권 분석 (rebo-search 호출)
     */
    public StoreAnalysisCache searchCategory(CategorySearchRequest request) {
        log.info("rebo-search 카테고리 검색 호출: {}", request);

        return restTemplate.postForObject(
                SEARCH_SERVICE_URL + "/api/stores/category/Cache",
                request,
                StoreAnalysisCache.class
        );
    }

    /**
     * StoreInput 저장 (rebo-search 호출)
     */
    public StoreInputResponse saveStoreInput(Long memberId, StoreInputSaveRequest request) {
        log.info("rebo-search StoreInput 저장 호출 - memberId: {}", memberId);

        return restTemplate.postForObject(
                SEARCH_SERVICE_URL + "/api/v1/store_input/save?memberId=" + memberId,
                request,
                StoreInputResponse.class
        );
    }

    /**
     * Region 저장 (rebo-search 호출)
     */
    public RegionResponse saveRegion(Long storeInputId, RegionSaveRequest request) {
        log.info("rebo-search Region 저장 호출 - storeInputId: {}", storeInputId);

        return restTemplate.postForObject(
                SEARCH_SERVICE_URL + "/api/v1/region/save?storeInputId=" + storeInputId,
                request,
                RegionResponse.class
        );
    }
}
