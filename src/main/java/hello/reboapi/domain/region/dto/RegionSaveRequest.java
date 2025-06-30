package hello.reboapi.domain.region.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionSaveRequest {
    
    private String roadAddress;
    private BigDecimal latitude;
    private BigDecimal longitude;
} 