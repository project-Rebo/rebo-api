package hello.reboapi.domain.search.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategorySearchRequest {

    @NotBlank(message = "키워드는 필수입니다")
    private String keyword;

    private String categoryLarge;
    private String categoryMiddle;
    private String categorySmall;

    private String searchName;

    @Min(value = 0, message = "반경은 0 이상이어야 합니다")
    private Integer radius;
}
