package hello.reboapi.domain.store_input.dto;

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