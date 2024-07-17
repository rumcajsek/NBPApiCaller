package pl.krol.springtrainer.objects;

import lombok.Data;

@Data
public class ATableAllCurrenciesRatesObject {
    private String currency;
    private String code;
    private double mid;
}
