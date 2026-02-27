package hello.reboapi.domain.report.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import hello.reboapi.domain.deepseek.dto.AnalysisResult;
import hello.reboapi.domain.deepseek.service.DeepSeekService;
import hello.reboapi.domain.report.dto.ReportAllSaveRequest;
import hello.reboapi.domain.report.dto.ReportResponse;
import hello.reboapi.domain.report.dto.ReportSimpleSaveRequest;
import hello.reboapi.domain.report.entity.Report;
import hello.reboapi.domain.report.repository.ReportRepository;
import hello.reboapi.domain.search.client.SearchServiceClient;
import hello.reboapi.domain.search.dto.CategorySearchRequest;
import hello.reboapi.domain.search.dto.StoreAnalysisCache;
import hello.reboapi.domain.search.dto.StoreInputResponse;
import hello.reboapi.domain.search.dto.RegionResponse;
import hello.reboapi.global.error.exception.BusinessException;
import hello.reboapi.global.error.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {

    private final DeepSeekService deepSeekService;
    private final SearchServiceClient searchServiceClient;
    private final ReportRepository reportRepository;

    /**
     * 상권 분석 리포트 생성 (카테고리 기반)
     */
    public ReportResponse generateCategoryReport(CategorySearchRequest request) {
        log.info("카테고리 기반 상권 분석 시작: {}", request);

        // 1. 매장 데이터 분석 (rebo-search 호출)
        StoreAnalysisCache analysisData = searchServiceClient.searchCategory(request);

        // 2. AI 텍스트 생성
        AnalysisResult analysisResult = deepSeekService.textTransFormScript(analysisData);

        // 3. 응답 객체 생성
        return ReportResponse.builder()
            .content(analysisResult.getContent())
            .score(analysisResult.getScore())
            .status(analysisResult.getStatus())
            .analysisData(analysisData)
            .request(request)
            .createdAt(LocalDateTime.now())
            .build();
    }

    /**
     * 리포트 단순 저장 (Report만)
     */
    @Transactional
    public Report saveReport(Long memberId, ReportSimpleSaveRequest request) {
        Report report = Report.builder()
            .memberId(memberId)
            .storeInputId(request.getStoreInputId())
            .totalStoreCount(request.getTotalStoreCount())
            .density(request.getDensity())
            .score(request.getScore().doubleValue())
            .status(request.getStatus())
            .content(request.getContent())
            .analysisData(request.getAnalysisData())
            .requestData(request.getRequestData())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        Report savedReport = reportRepository.save(report);
        log.info("Report 저장 완료 - id: {}", savedReport.getId());
        return savedReport;
    }

    /**
     * 전체 데이터 저장 (StoreInput + Region + Report)
     */
    @Transactional
    public ReportResponse saveReportWithAllData(Long memberId, ReportAllSaveRequest request) {
        try {
            log.info("전체 데이터 저장 시작 - memberId: {}", memberId);

            // 1. StoreInput 저장 (rebo-search 호출)
            StoreInputResponse storeInput = searchServiceClient.saveStoreInput(memberId, request.getStoreInputData());
            log.info("StoreInput 저장 완료 - id: {}", storeInput.getId());

            // 2. Region 저장 (rebo-search 호출)
            RegionResponse region = searchServiceClient.saveRegion(storeInput.getId(), request.getRegionData());
            log.info("Region 저장 완료 - id: {}", region.getId());

            // 3. Report 저장 (StoreInput.id를 storeInputId로 사용)
            ReportSimpleSaveRequest reportRequest = ReportSimpleSaveRequest.builder()
                .storeInputId(storeInput.getId())
                .content(request.getContent())
                .score(request.getScore())
                .status(request.getStatus())
                .totalStoreCount(request.getTotalStoreCount())
                .density(request.getDensity())
                .analysisData(request.getAnalysisData())
                .requestData(request.getRequestData())
                .build();

            Report report = this.saveReport(memberId, reportRequest);
            log.info("Report 저장 완료 - id: {}", report.getId());

            // 4. 응답 객체 생성
            return ReportResponse.builder()
                .content(report.getContent())
                .score(report.getScore().intValue())
                .status(report.getStatus())
                .createdAt(report.getCreatedAt())
                .build();

        } catch (Exception e) {
            log.error("전체 데이터 저장 실패 - memberId: {}, error: {}", memberId, e.getMessage());
            throw new BusinessException(ErrorCode.INVALID_USER_INPUT);
        }
    }

    /**
     * 회원별 리포트 목록 조회
     */
    public List<Report> getMemberReports(Long memberId) {
        List<Report> reports = reportRepository.findByMemberIdOrderByCreatedAtDesc(memberId);

        return reports;
    }
}
