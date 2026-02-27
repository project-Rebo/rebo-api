package hello.reboapi.domain.report.service;

import hello.reboapi.domain.deepseek.dto.AnalysisResult;
import hello.reboapi.domain.deepseek.service.DeepSeekService;
import hello.reboapi.domain.report.dto.ReportResponse;
import hello.reboapi.domain.report.repository.ReportRepository;
import hello.reboapi.domain.search.client.SearchServiceClient;
import hello.reboapi.domain.search.dto.CategorySearchRequest;
import hello.reboapi.domain.search.dto.StoreAnalysisCache;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @InjectMocks
    private ReportService reportService;

    @Mock
    private DeepSeekService deepSeekService;

    @Mock
    private SearchServiceClient searchServiceClient;

    @Mock
    private ReportRepository reportRepository;

    @Test
    @DisplayName("카테고리 기반 리포트 생성 성공 - SearchServiceClient 사용")
    void generateCategoryReport_success() {
        // given
        CategorySearchRequest request = new CategorySearchRequest(
                "강남역", "음식", null, null, "치킨", 1000
        );

        StoreAnalysisCache analysisData = StoreAnalysisCache.builder()
                .region("강남역")
                .categoryLarge("음식")
                .totalStores(50)
                .density(15.9)
                .radius(1000)
                .latitude(37.498095)
                .longitude(127.02761)
                .build();

        AnalysisResult analysisResult = AnalysisResult.builder()
                .content("분석 결과 내용")
                .score(75)
                .status("중립")
                .build();

        given(searchServiceClient.searchCategory(any(CategorySearchRequest.class)))
                .willReturn(analysisData);
        given(deepSeekService.textTransFormScript(any(StoreAnalysisCache.class)))
                .willReturn(analysisResult);

        // when
        ReportResponse result = reportService.generateCategoryReport(request);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo("분석 결과 내용");
        assertThat(result.getScore()).isEqualTo(75);
        assertThat(result.getStatus()).isEqualTo("중립");
        assertThat(result.getAnalysisData()).isNotNull();
        assertThat(result.getAnalysisData().getTotalStores()).isEqualTo(50);
    }
}
