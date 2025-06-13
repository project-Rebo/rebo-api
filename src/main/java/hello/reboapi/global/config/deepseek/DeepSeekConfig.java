package hello.reboapi.global.config.deepseek;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableConfigurationProperties(DeepSeekProperties.class)
public class DeepSeekConfig {
    private final DeepSeekProperties properties;

    public DeepSeekConfig(DeepSeekProperties properties) {
        this.properties = properties;
        log.info("DeepSeek Config initialized - URL: {}, Model: {}, Key: {}...", 
            properties.getUrl(), 
            properties.getModel(), 
            properties.getKey() != null ? properties.getKey().substring(0, Math.min(10, properties.getKey().length())) : "null");
    }

    @Bean("deepseekRestTemplate")
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        template.getInterceptors().add((request, body, execution) -> {
            String auth = properties.getAuthorizationHeader();
            log.debug("DeepSeek Authorization Header: {}", auth.length() > 20 ? auth.substring(0, 20) + "..." : auth);
            request.getHeaders().add("Authorization", auth);
            return execution.execute(request, body);
        });
        return template;
    }
}

