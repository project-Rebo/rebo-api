package hello.reboapi.domain.kakao.service;

import hello.reboapi.domain.kakao.dto.KakaoGeocodeResponse;
import hello.reboapi.global.config.kakao.KakaoGeocodingClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private final KakaoGeocodingClient kakaoGeocodingClient;

    @Transactional
    public ResponseEntity<KakaoGeocodeResponse> searchKeyword(String keyword) {
        KakaoGeocodeResponse geocodeResponse = kakaoGeocodingClient.getGeocode(keyword);

        return ResponseEntity.ok(geocodeResponse);
    }
}
