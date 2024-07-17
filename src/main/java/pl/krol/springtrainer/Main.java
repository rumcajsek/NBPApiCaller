package pl.krol.springtrainer;

import pl.krol.springtrainer.objects.ATableAllCurrenciesObject;
import pl.krol.springtrainer.objects.ATableCurrencyObject;
import pl.krol.springtrainer.rest.ApiRestClient;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApiRestClient apiRestClient = new ApiRestClient();
        ATableCurrencyObject euroStatus = apiRestClient.getAnyCurrencyDataReturnATable("EUR");
        ATableCurrencyObject dollarStatus = apiRestClient.getAnyCurrencyDataReturnATable("USD");
        List<ATableAllCurrenciesObject> allCurrencies = apiRestClient.getAllCurrenciesDataReturnATable();
        System.out.println(euroStatus);
        System.out.println(dollarStatus);
        System.out.println(allCurrencies);
    }
}