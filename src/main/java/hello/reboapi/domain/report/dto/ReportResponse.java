package hello.reboapi.domain.report.dto;

import java.time.LocalDateTime;

import hello.reboapi.domain.store.dto.CategorySearchRequest;
import hello.reboapi.global.config.cache.dto.StoreAnalysisCache;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportResponse {

    private String content;
    private Integer score;
    private String status;
    private StoreAnalysisCache analysisData;
    private CategorySearchRequest request;
    private LocalDateTime createdAt;
}
