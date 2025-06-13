package hello.reboapi.domain.store.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import java.util.List;
import hello.reboapi.domain.store.dto.SearchStoreRequest;
import hello.reboapi.domain.store.entity.Store;
import hello.reboapi.domain.store.service.StoreService;
import hello.reboapi.global.config.cache.dto.StoreAnalysisCache;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    // 상권 검색(리스트 변환)
    @PostMapping
    public ResponseEntity<List<Store>> searchStore(@Valid @RequestBody SearchStoreRequest request) {
        List<Store> stores = storeService.searchStore(request);
        return ResponseEntity.ok(stores);
    }

    // 상권 분석 보고서
    @PostMapping("/analyze")
    public ResponseEntity<String> analyzeStore(@Valid @RequestBody SearchStoreRequest request) {

        String aiAnalysis = storeService.AIAnalysisReport(request);
        return ResponseEntity.ok(aiAnalysis);
    }

    // 캐시 데이터 조회
    @PostMapping("/cache")
    public ResponseEntity<StoreAnalysisCache> getCacheData(@Valid @RequestBody SearchStoreRequest request) {
        
        StoreAnalysisCache cacheData = storeService.analyzeStoreData(request);
        return ResponseEntity.ok(cacheData);
    }
}
