package hello.reboapi.domain.store.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import hello.reboapi.domain.store.service.StoreService;
import hello.reboapi.global.config.cache.dto.StoreAnalysisCache;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;
import hello.reboapi.domain.store.dto.CategorySearchRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/stores")
@Tag(name = "Store", description = "상점 검색 및 분석 API")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @Operation(summary = "카테고리 기반 상권 분석", description = "지역, 카테고리, 반경 기준으로 상권 통계를 분석합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "분석 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청: 필수 파라미터 누락"),
        @ApiResponse(responseCode = "404", description = "분석 대상 데이터 없음")
    })
    @PostMapping("/category/Cache")
    public ResponseEntity<StoreAnalysisCache> generateCategory(
            @Valid @RequestBody CategorySearchRequest request) {
        return ResponseEntity.ok(storeService.generateCategory(request));
    }

    @Operation(summary = "카테고리 기반 상권 분석 리포트", description = "지역, 카테고리, 반경 기준으로 AI 분석 리포트를 생성합니다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "리포트 생성 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청: 필수 파라미터 누락"),
        @ApiResponse(responseCode = "404", description = "분석 대상 데이터 없음"),
        @ApiResponse(responseCode = "500", description = "AI 서비스 오류")
    })
    @PostMapping("/category/report")
    public ResponseEntity<String> generateCategoryReport(
            @Valid @RequestBody CategorySearchRequest request) {
        return ResponseEntity.ok(storeService.generateCategoryReport(request));
    }
}
