package stylepatrick.ethGasFeeNotifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EthGasFeeNotifierApplication {

	public static void main(String[] args) {
		SpringApplication.run(EthGasFeeNotifierApplication.class, args);
	}

}
