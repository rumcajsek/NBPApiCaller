package pl.krol.springtrainer.rest;

import pl.krol.springtrainer.exceptions.ApiRestCallException;
import pl.krol.springtrainer.objects.ATableCurrencyObject;
import lombok.Data;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.net.URI;

@Data
public class ApiRestClient {
    private RestClient restClient = RestClient.create();
    private final String GENERIC_API_ADDRESS = "http://api.nbp.pl/api/exchangerates/rates/A/";

    public ATableCurrencyObject getEuroCurrencyDataReturnATable() {
        return restClient.get()
                .uri(URI.create(GENERIC_API_ADDRESS + "EUR/"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    throw new ApiRestCallException(response);
                }))
                .body(ATableCurrencyObject.class);
    }

    public ATableCurrencyObject getAnyCurrencyDataReturnATable(String currencyCode) {
        return restClient.get()
                .uri(URI.create(GENERIC_API_ADDRESS + currencyCode + "/"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    throw new ApiRestCallException(response);
                }))
                .body(ATableCurrencyObject.class);
    }
}
