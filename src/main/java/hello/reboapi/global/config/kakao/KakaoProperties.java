package hello.reboapi.global.config.kakao;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "kakao")
public class KakaoProperties {
    private String apiKey;
    private String adminKey;
    private String baseUrl;

    public String getAuthorizationHeader() {
        return "KakaoAK " + apiKey;
    }
}
