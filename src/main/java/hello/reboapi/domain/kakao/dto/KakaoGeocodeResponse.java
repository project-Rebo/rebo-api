package hello.reboapi.domain.kakao.dto;

import lombok.Getter;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
public class KakaoGeocodeResponse {
    private final Double latitude;
    private final Double longitude;
    private final String placeName;
    private final String roadAddress;

    public KakaoGeocodeResponse(String latitude, String longitude, String placeName, String roadAddress) {
        this.latitude = BigDecimal.valueOf(Double.parseDouble(latitude))
                .setScale(6, RoundingMode.HALF_UP)
                .doubleValue();
        this.longitude = BigDecimal.valueOf(Double.parseDouble(longitude))
                .setScale(6, RoundingMode.HALF_UP)
                .doubleValue();
        this.placeName = placeName;
        this.roadAddress = roadAddress;
    }
}