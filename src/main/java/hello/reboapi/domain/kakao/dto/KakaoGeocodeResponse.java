package hello.reboapi.domain.kakao.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoGeocodeResponse {
    private String latitude;
    private String longitude;
    private String placeName;
}