package pl.krol.springtrainer;

import pl.krol.springtrainer.objects.ATableCurrencyObject;
import pl.krol.springtrainer.rest.ApiRestClient;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ApiRestClient apiRestClient = new ApiRestClient();
        ATableCurrencyObject euroStatus = apiRestClient.getEuroCurrencyDataReturnATable();
        ATableCurrencyObject dollarStatus = apiRestClient.getAnyCurrencyDataReturnATable("USD");
        System.out.println(euroStatus);
        System.out.println(dollarStatus);
    }
}