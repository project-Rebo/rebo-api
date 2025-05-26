package hello.reboapi.global.config.kakao;

import hello.reboapi.domain.kakao.dto.KakaoGeocodeResponse;
import hello.reboapi.domain.kakao.dto.KakaoKeywordSearchRawResponse;
import hello.reboapi.global.error.exception.BusinessException;
import hello.reboapi.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.math.BigDecimal;
import java.math.RoundingMode;

import java.net.URI;
import org.springframework.http.HttpHeaders;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoGeocodingClient {

    private final KakaoProperties kakaoProperties;
    private final RestTemplate restTemplate;

    public KakaoGeocodeResponse getGeocode(String query) {
        // 1. Header 구성
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", kakaoProperties.getAuthorizationHeader());
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<?> entity = new HttpEntity<>(headers);

        log.info("api key: {}", kakaoProperties.getApiKey());

        // 2. URI 구성
        URI uri = UriComponentsBuilder
                .fromHttpUrl(kakaoProperties.getBaseUrl() + "/search/keyword.json")
                .queryParam("query", query)
                .build()
                .encode()
                .toUri();

        // 3. 요청
        ResponseEntity<KakaoKeywordSearchRawResponse> response =
                restTemplate.exchange(uri, HttpMethod.GET, entity, KakaoKeywordSearchRawResponse.class);

        // 4. 결과 파싱 (첫 번째 항목만)
        List<KakaoKeywordSearchRawResponse.Document> docs = response.getBody().getDocuments();

        if (docs.isEmpty()) {
            throw new BusinessException(ErrorCode.KAKAO_NOT_FOUND);
        }

        KakaoKeywordSearchRawResponse.Document top = docs.get(0);

        String dong = null;
        for (String word : top.getAddress_name().split(" ")) {
            if (word.matches("^[가-힣]{1,2}동$")) {
                dong = word;
                break;
            }
        }

        if (dong == null) {
            throw new BusinessException(ErrorCode.KAKAO_NOT_FOUND);
        }

        return new KakaoGeocodeResponse(top.getY(), top.getX(), dong);
    }
}
