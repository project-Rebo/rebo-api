package hello.reboapi.domain.search.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreInputResponse {

    private Long id;
    private Long memberId;
    private String location;
    private String category;
    private Integer radius;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
