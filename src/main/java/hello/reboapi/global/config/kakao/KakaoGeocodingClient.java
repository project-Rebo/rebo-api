package hello.reboapi.global.config;

import hello.reboapi.domain.kakao.dto.KakaoGeocodeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import org.springframework.http.HttpHeaders;
import java.util.List;

@Component
@RequiredArgsConstructor
public class KakaoGeocodingClient {

    private final KakaoProperties kakaoProperties;
    private final RestTemplate restTemplate;

    public KakaoGeocodeResponse getGeocode(String query) {

        // 1. Header 구성
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", kakaoProperties.getAuthrizationHeader());
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<?> entity = new HttpEntity<>(headers);

        // 2. URI 구성
        URI uri = UriComponentsBuilder
                .fromHttpUrl(kakaoProperties.getBaseUrl() + "/search/address.json")
                .queryParam("query", query)
                .build()
                .encode()
                .toUri();

        // 3. 요청
        ResponseEntity<KakaoGeocodeResponse> response =
                restTemplate.exchange(uri, HttpMethod.GET, entity, KakaoGeocodeResponse.class);

        return response.getBody();
    }
}
