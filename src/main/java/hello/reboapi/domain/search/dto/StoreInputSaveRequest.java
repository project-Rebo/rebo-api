package hello.reboapi.domain.search.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreInputSaveRequest {

    private String location;
    private String category;
    private Integer radius;
}
