package hello.reboapi.domain.kakao.service;

import hello.reboapi.domain.kakao.dto.KakaoGeocodeResponse;
import hello.reboapi.global.config.kakao.KakaoGeocodingClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private final KakaoGeocodingClient kakaoGeocodingClient;

    @Transactional
    public KakaoGeocodeResponse searchKeyword(String keyword) {
        return kakaoGeocodingClient.getGeocode(keyword);
    }
}
