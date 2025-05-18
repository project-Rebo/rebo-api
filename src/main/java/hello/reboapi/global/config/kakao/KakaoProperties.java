package hello.reboapi.global.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "kakao")
public class KakaoProperties {
    private String apiKey;
    private String adminKey;
    private String baseUrl;

    public String getAuthrizationHeader() {
        return "KakaoAk" + apiKey;
    }
}
