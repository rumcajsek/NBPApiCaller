package pl.krol.springtrainer.objects;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ATableAllCurrenciesObject {
    private String table;
    private String no;
    private LocalDate effectiveDate;
    private List<ATableAllCurrenciesRatesObject> rates;
}
