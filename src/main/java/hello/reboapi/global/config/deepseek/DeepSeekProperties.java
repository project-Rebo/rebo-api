package hello.reboapi.global.config.deepseek;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "deepseek.api")
public class DeepSeekProperties {

    private String key;
    private String url;
    private String model;

    public String getAuthorizationHeader() {
        return "Bearer " + key;
    }
}
