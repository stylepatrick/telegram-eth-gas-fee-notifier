package stylepatrick.ethGasFeeNotifier.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GasOracleResult {

    @JsonProperty("LastBlock")
    private String lastBlock;

    @JsonProperty("SafeGasPrice")
    private String safeGasPrice;

    @JsonProperty("ProposeGasPrice")
    private String ProposeGasPrice;

    @JsonProperty("FastGasPrice")
    private String fastGasPrice;

    private String suggestBaseFee;
    private String gasUsedRatio;
}
