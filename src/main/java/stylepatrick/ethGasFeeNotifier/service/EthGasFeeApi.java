package stylepatrick.ethGasFeeNotifier.service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import stylepatrick.ethGasFeeNotifier.config.AppConfig;
import stylepatrick.ethGasFeeNotifier.model.GasOracle;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

@Service
@AllArgsConstructor
public class EthGasFeeApi {

    private final AppConfig appConfig;
    private static GasOracle previous;
    private static boolean sendState = true;

    @Scheduled(fixedDelay = 10000)
    private void scheduledGasFeeCall() {
        if (previous == null) {
            previous = getGasFee();
        } else {
            GasOracle current = getGasFee();
            double percentage = Math.abs(((Integer.parseInt(current.getResult().getProposeGasPrice()) * 100) /Integer.parseInt(previous.getResult().getProposeGasPrice())) - 100);
            if (Integer.parseInt(current.getResult().getProposeGasPrice()) > appConfig.getTriggerGasPrice() && !sendState) {
                sendTelegramNotification(current);
                sendState = true;
            } else if (Integer.parseInt(current.getResult().getProposeGasPrice()) <= appConfig.getTriggerGasPrice() && percentage >= 20 && !sendState) {
                sendTelegramNotification(current);
            } else if (Integer.parseInt(current.getResult().getProposeGasPrice()) <= appConfig.getTriggerGasPrice() && sendState) {
                sendTelegramNotification(current);
                sendState = false;
            }
            previous = current;
        }
    }

    private GasOracle getGasFee() {
        RestTemplate restTemplate = new RestTemplate();
        String url = appConfig.getEtherscanApi() + "?module=gastracker&action=gasoracle&apikey=" + appConfig.getEtherscanToken();
        ResponseEntity<GasOracle> response = restTemplate.getForEntity(url, GasOracle.class);
        return response.getBody();
    }

    private void sendTelegramNotification(GasOracle gasOracle) {
        String urlString = appConfig.getTelegramApi() + "/bot%s/sendMessage?chat_id=%s&text=%s&parse_mode=HTML";
        String text = "<b>Propose Gas Price: </b>" + gasOracle.getResult().getProposeGasPrice() + "\n" +
                "<b>Fast Gas Price: </b>" + gasOracle.getResult().getFastGasPrice()+ "\n" +
                "<b>Safe Gas Price: </b>" + gasOracle.getResult().getSafeGasPrice();
        urlString = String.format(urlString, appConfig.getTelegramToken(), appConfig.getTelegramChatId(), URLEncoder.encode(text));

        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            InputStream is = new BufferedInputStream(conn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
