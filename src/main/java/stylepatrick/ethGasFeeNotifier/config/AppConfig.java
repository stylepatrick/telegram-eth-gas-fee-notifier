package stylepatrick.ethGasFeeNotifier.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "notifier")
@Getter
@Setter
public class AppConfig {

    private Integer triggerGasPrice;
    private String telegramApi;
    private String telegramToken;
    private String telegramChatId;
    private String etherscanApi;
    private String etherscanToken;

}
