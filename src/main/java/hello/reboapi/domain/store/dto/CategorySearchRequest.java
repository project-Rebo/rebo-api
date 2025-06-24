package hello.reboapi.domain.store.dto;

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
    private String keyword;    // 주소 검색용

    private String categoryLarge;    // 필수
    private String categoryMiddle;   // 선택
    private String categorySmall;    // 선택

    private String searchName;    // 검색 이름
    
    @Min(value = 0, message = "반경은 0 이상이어야 합니다")
    private Integer radius;
}