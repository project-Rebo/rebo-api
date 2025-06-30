package hello.reboapi.domain.report.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportSimpleSaveRequest {
    
    private Long storeInputId;
    private String content;
    private Integer score;
    private String status;
    private Integer totalStoreCount;
    private Double density;
    private String analysisData;
    private String requestData;
} 