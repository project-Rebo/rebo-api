package hello.reboapi.global.config.kakao;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableConfigurationProperties(KakaoProperties.class)
@RequiredArgsConstructor
public class KakaoConfig {
    private final KakaoProperties kakaoProperties;
    
    @Bean("kakaoRestTemplate")
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        template.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "KakaoAK " + kakaoProperties.getApiKey());
            return execution.execute(request, body);
        });
        
        return template;
    }
}