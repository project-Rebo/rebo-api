package hello.reboapi.domain.store.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchStoreRequest {
    @NotBlank(message = "키워드는 필수입니다")
    private String keyword;
    
    @NotBlank(message = "카테고리는 필수입니다")
    private String category;
    
    @Min(value = 0, message = "반경은 0 이상이어야 합니다")
    private Integer radius;
}