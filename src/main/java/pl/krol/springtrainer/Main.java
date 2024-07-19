package pl.krol.springtrainer;

import pl.krol.springtrainer.exceptions.UnallowedFieldValueException;
import pl.krol.springtrainer.objects.*;
import pl.krol.springtrainer.rest.ApiRestClient;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) throws UnallowedFieldValueException, IllegalAccessException {
        ApiRestClient apiRestClient = new ApiRestClient();
        ATableTableObject euroStatus = apiRestClient.getAnyCurrencyDataReturnATable("EUR");
        ATableTableObject dollarStatus = apiRestClient.getAnyCurrencyDataReturnATable("USD");
        List<AandBTableTablesObject> allCurrencies = apiRestClient.getAllCurrenciesDataReturnATable();

        SingleCurrencyParameters params = new SingleCurrencyParameters.Builder()
                .table("A")
                .code("EUR")
                .lastOrToday("last")
                .topCount(3L)
                .startDate(LocalDate.of(2024,1,1))
                .endDate(LocalDate.of(2024,5,31))
                .build();
        ATableTableObject something = apiRestClient.alternativeRestCallWithBuilderParameter(params);
        System.out.println(euroStatus);
        System.out.println(dollarStatus);
        System.out.println(allCurrencies);
        System.out.println(something);
    }
}