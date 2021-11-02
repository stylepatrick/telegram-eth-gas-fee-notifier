package stylepatrick.ethGasFeeNotifier.model;

import lombok.*;

@NoArgsConstructor
@Getter
public class GasOracle {

    private String status;
    private String message;
    private GasOracleResult result;

}
