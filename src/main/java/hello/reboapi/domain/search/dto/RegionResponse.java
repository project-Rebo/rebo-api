package hello.reboapi.domain.search.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegionResponse {

    private Long id;
    private Long storeInputId;
    private String roadAddress;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
