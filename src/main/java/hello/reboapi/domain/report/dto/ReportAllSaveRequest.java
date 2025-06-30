package hello.reboapi.domain.report.dto;

import hello.reboapi.domain.region.dto.RegionSaveRequest;
import hello.reboapi.domain.store_input.dto.StoreInputSaveRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportAllSaveRequest {
    
    // StoreInput 데이터
    private StoreInputSaveRequest storeInputData;
    
    // Region 데이터
    private RegionSaveRequest regionData;
    
    // Report 데이터
    private String content;
    private Integer score;
    private String status;
    private Integer totalStoreCount;
    private Double density;
    
    // 분석 데이터
    private String analysisData;
    
    // 요청 데이터
    private String requestData;
} 