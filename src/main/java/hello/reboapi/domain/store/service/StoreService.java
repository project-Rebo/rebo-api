package hello.reboapi.domain.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import hello.reboapi.domain.kakao.service.KakaoService;
import hello.reboapi.domain.store.dto.CategorySearchRequest;
import hello.reboapi.domain.kakao.dto.KakaoGeocodeResponse;
import hello.reboapi.domain.store.repository.StoreRepository;
import hello.reboapi.global.config.cache.dto.StoreAnalysisCache;
import hello.reboapi.global.error.exception.BusinessException;
import hello.reboapi.global.error.exception.ErrorCode;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {
 
    private final KakaoService kakaoService;
    private final StoreRepository storeRepository;

    // 빈 문자열을 null로 취급하는 유틸리티 메서드
    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

     // 카테고리 기반 검색 (캐시)
    @Cacheable(value = "store-analysis", key = "#request.keyword + '-' + #request.categoryLarge + '-' + #request.categoryMiddle + '-' + #request.categorySmall + '-' + #request.searchName + '-' + #request.radius")
    public StoreAnalysisCache generateCategory(CategorySearchRequest request) {
        long totalStores = 0;
        
        log.info("=== 카테고리 검색 시작 ===");
        log.info("Request: {}", request);
            
        // 요청 파라미터 검증
        if (isNullOrEmpty(request.getSearchName()) || isNullOrEmpty(request.getKeyword()) || request.getRadius() == null) {
            log.error("잘못된 요청 파라미터: searchName={}, keyword={}, radius={}", request.getSearchName(), request.getKeyword(), request.getRadius());
            
            throw new BusinessException(ErrorCode.INVALID_STORE_INPUT);
        }
    
        // 지오코딩 결과 조회
        KakaoGeocodeResponse geocode = kakaoService.searchKeyword(request.getKeyword());
        log.info("지오코딩 결과: lat={}, lng={}", geocode.getLatitude(), geocode.getLongitude());
    
        // count 조회(대분류만) - 빈 문자열도 null로 취급
        if (!isNullOrEmpty(request.getCategoryLarge()) && isNullOrEmpty(request.getCategoryMiddle()) && isNullOrEmpty(request.getCategorySmall())) {
            log.info(">>> 대분류 쿼리 실행");
            
            totalStores = storeRepository.countStoresByCategoryLarge(
            geocode.getLatitude(),
            geocode.getLongitude(),
            request.getRadius(),
            request.getCategoryLarge(),
            request.getSearchName()
            );
            
            log.info("대분류 조회 결과: {}개", totalStores);
        }

        // count 조회(중분류만)
        else if (!isNullOrEmpty(request.getCategoryMiddle()) && isNullOrEmpty(request.getCategoryLarge()) && isNullOrEmpty(request.getCategorySmall())) {
            log.info(">>> 중분류 쿼리 실행");
            
            totalStores = storeRepository.countStoresByCategoryMiddle(
                geocode.getLatitude(),
                geocode.getLongitude(),
                request.getRadius(),
                request.getCategoryMiddle(),
                request.getSearchName()
            );
            
            log.info("중분류 조회 결과: {}개", totalStores);
        }

        // count 조회(소분류만)
        else if (!isNullOrEmpty(request.getCategorySmall()) && isNullOrEmpty(request.getCategoryLarge()) && isNullOrEmpty(request.getCategoryMiddle())) {
            log.info(">>> 소분류 쿼리 실행");
            
            totalStores = storeRepository.countStoresByCategorySmall(
                geocode.getLatitude(),
                geocode.getLongitude(),
                request.getRadius(),
                request.getCategorySmall(),
                request.getSearchName()
            );
            
            log.info("소분류 조회 결과: {}개", totalStores);
        }

        // count 조회(전체 - 여러 카테고리가 있을 때)
        else {
            log.info(">>> 전체 카테고리 쿼리 실행");
            
            totalStores = storeRepository.countStoresByCategoryAll(
                geocode.getLatitude(),
                geocode.getLongitude(),
                request.getRadius(),
                request.getCategoryLarge(),
                request.getCategoryMiddle(),
                request.getCategorySmall(),
                request.getSearchName()
            );
            
            log.info("전체 카테고리 조회 결과: {}개", totalStores);
        }
        
        log.info("최종 조회 결과: {}개", totalStores);
        log.info("===================");
    
        // 조회된 매장이 0개인 경우 예외 처리
        if(totalStores == 0) {
            log.warn("조회된 매장이 0개입니다. 검색 조건을 확인해주세요.");
            throw new BusinessException(ErrorCode.STORE_LIST_EMPTY);
        }
    
        // density 계산
        double area = Math.PI * Math.pow(request.getRadius(), 2) / 1_000_000;
        double density = totalStores / area;
        
        log.info("밀도 계산: area={}km², density={}", area, density);
    
        // 캐시 데이터 생성
        StoreAnalysisCache analysisData = StoreAnalysisCache.builder()
            .region(request.getKeyword())
            .categoryLarge(request.getCategoryLarge())
            .categoryMiddle(request.getCategoryMiddle())
            .categorySmall(request.getCategorySmall())
            .roadAddress(geocode.getRoadAddress())
            .placeName(geocode.getPlaceName())
            .radius(request.getRadius())
            .density(density)
            .totalStores(totalStores)
            .latitude(geocode.getLatitude())   
            .longitude(geocode.getLongitude())  
            .build();

        return analysisData;     
    }
}
