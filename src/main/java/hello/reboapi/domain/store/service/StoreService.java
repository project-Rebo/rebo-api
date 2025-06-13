package hello.reboapi.domain.store.service;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import hello.reboapi.domain.kakao.service.KakaoService;
import hello.reboapi.domain.store.dto.SearchStoreRequest;
import hello.reboapi.domain.deepseek.service.DeepSeekService;
import hello.reboapi.domain.kakao.dto.KakaoGeocodeResponse;
import hello.reboapi.domain.store.entity.Store;
import hello.reboapi.domain.store.repository.StoreRepository;
import hello.reboapi.global.config.cache.dto.StoreAnalysisCache;
import hello.reboapi.global.error.exception.BusinessException;
import hello.reboapi.global.error.exception.ErrorCode;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final KakaoService kakaoService;
    private final StoreRepository storeRepository;
    private final DeepSeekService deepSeekService;

    //상가 검색(리스트 반환)
    public List<Store> searchStore(SearchStoreRequest request) {

        try {
            if(request.getKeyword() == null || request.getCategory() == null || request.getRadius() == null) {
                throw new BusinessException(ErrorCode.INVALID_STORE_INPUT);
            }

            KakaoGeocodeResponse geocodeResponse = kakaoService.searchKeyword(request.getKeyword());

            return storeRepository.findStoresByLocationAndCategory(
                geocodeResponse.getLatitude(),
                geocodeResponse.getLongitude(), 
                request.getRadius(), 
                request.getCategory()
            );
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.STORE_ANALYSIS_FAILED);
        }
    }

    //상권 분석 서비스코드(분석 데이터만 처리)
    @Cacheable(value = "store-analysis", key = "#request.keyword + '-' + #request.category + '-' + #request.radius")
    public StoreAnalysisCache analyzeStoreData(SearchStoreRequest request) {

        try {
            if(request.getKeyword() == null || request.getCategory() == null || request.getRadius() == null) {
                throw new BusinessException(ErrorCode.INVALID_STORE_INPUT);
            }
            //카카오 좌표 변환
            KakaoGeocodeResponse geocodeResponse = kakaoService.searchKeyword(request.getKeyword());

            //상권 매장 조회
            List<Store> stores = storeRepository.findStoresByLocationAndCategory(
                geocodeResponse.getLatitude(),
                geocodeResponse.getLongitude(), 
                request.getRadius(), 
                request.getCategory()
            );

            if(stores.isEmpty()) {
                throw new BusinessException(ErrorCode.STORE_LIST_EMPTY);
            }

            double area = Math.PI * Math.pow(request.getRadius(), 2) / 1_000_000;
            double density = stores.size() / area;

            return StoreAnalysisCache.builder()
                .region(request.getKeyword())
                .category(request.getCategory())
                .radius(request.getRadius())
                .desity(density)
                .totalStores(stores.size())
                .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.STORE_ANALYSIS_FAILED);
        }
    }

    //상권 분석 리포트 생성
    public String AIAnalysisReport(SearchStoreRequest request) {

        try {
            StoreAnalysisCache storeAnalysisCache = analyzeStoreData(request);
            
            return deepSeekService.textTransFormScript(storeAnalysisCache);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.STORE_ANALYSIS_FAILED);
        }
    }
}
