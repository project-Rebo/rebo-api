package hello.reboapi.domain.kakao.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
public class KakaoKeywordSearchRawResponse {
    private List<Document> documents;

    @Getter
    @NoArgsConstructor
    public static class Document {
        private String place_name;
        private String address_name;
        private String x; // longitude
        private String y; // latitude
    }
}
