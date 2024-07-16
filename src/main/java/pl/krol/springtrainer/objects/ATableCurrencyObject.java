package pl.krol.springtrainer.objects;

import lombok.*;

import java.util.List;

@Data
public class ATableCurrencyObject {
    private String table;
    private String currency;
    private String code;
    private List<ATableRatesObject> rates;
}
