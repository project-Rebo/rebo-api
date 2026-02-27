package hello.reboapi.domain.report.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.reboapi.domain.report.dto.ReportAllSaveRequest;
import hello.reboapi.domain.report.dto.ReportResponse;
import hello.reboapi.domain.report.dto.ReportSimpleSaveRequest;
import hello.reboapi.domain.report.entity.Report;
import hello.reboapi.domain.report.service.ReportService;
import hello.reboapi.domain.search.dto.CategorySearchRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Report", description = "상권 분석 리포트 API")
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "카테고리 기반 상권 분석 리포트", description = "지역, 카테고리, 반경 기준으로 AI 분석 리포트를 생성합니다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "리포트 생성 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청: 필수 파라미터 누락"),
        @ApiResponse(responseCode = "404", description = "분석 대상 데이터 없음"),
        @ApiResponse(responseCode = "500", description = "AI 서비스 오류")
    })
    @PostMapping("/analysis")
    public ResponseEntity<ReportResponse> generateCategoryReport(
            @Valid @RequestBody CategorySearchRequest request) {
        
        ReportResponse response = reportService.generateCategoryReport(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "리포트 단순 저장", description = "기존 StoreInput과 연결된 리포트만 저장합니다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "리포트 저장 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청: 필수 파라미터 누락"),
        @ApiResponse(responseCode = "404", description = "StoreInput을 찾을 수 없음")
    })
    @PostMapping("/save-simple")
    public ResponseEntity<Report> saveSimpleReport(
            @RequestParam Long memberId, 
            @Valid @RequestBody ReportSimpleSaveRequest request) {
        
        Report report = reportService.saveReport(memberId, request);
        return ResponseEntity.ok(report);
    }

    @Operation(summary = "전체 데이터 저장", description = "StoreInput, Region, Report를 모두 저장합니다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "전체 데이터 저장 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청: 필수 파라미터 누락"),
        @ApiResponse(responseCode = "500", description = "저장 프로세스 오류")
    })
    @PostMapping("/save-all")
    public ResponseEntity<ReportResponse> saveReportWithAllData(@RequestParam Long memberId, @Valid @RequestBody ReportAllSaveRequest request) {
        
        ReportResponse response = reportService.saveReportWithAllData(memberId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "회원별 리포트 목록 조회", description = "특정 회원의 모든 리포트를 조회합니다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "리포트 목록 조회 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청: 회원 ID 누락")
    })
    @GetMapping("list/{memberId}")
    public ResponseEntity<List<Report>> getMemberReports(@RequestParam Long memberId) {
        
        List<Report> reports = reportService.getMemberReports(memberId);
        return ResponseEntity.ok(reports);
    }
}
