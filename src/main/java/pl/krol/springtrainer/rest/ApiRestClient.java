package pl.krol.springtrainer.rest;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import pl.krol.springtrainer.exceptions.ApiRestCallException;
import pl.krol.springtrainer.objects.ATableAllCurrenciesObject;
import pl.krol.springtrainer.objects.ATableCurrencyObject;
import lombok.Data;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.lang.reflect.Type;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class ApiRestClient {
    private final String GENERIC_EXCHANGE_RATE_API_ADDRESS = "http://api.nbp.pl/api/exchangerates/rates/A/";
    private final String ALL_CURRENCIES_TABLE_API_ADDRESS = "http://api.nbp.pl/api/exchangerates/tables/A/";

    private RestClient restClient = RestClient.create();
    private Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
        @Override
        public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return LocalDate.parse(jsonElement.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }).create();

    public ATableCurrencyObject getAnyCurrencyDataReturnATable(String currencyCode) {
        return restClient.get()
                .uri(URI.create(GENERIC_EXCHANGE_RATE_API_ADDRESS + currencyCode + "/"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new ApiRestCallException(response);
                }))
                .body(ATableCurrencyObject.class);
    }

    public List<ATableAllCurrenciesObject> getAllCurrenciesDataReturnATable() {
        String jsonResponse = restClient.get()
                .uri(ALL_CURRENCIES_TABLE_API_ADDRESS)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new ApiRestCallException(response);
                }))
                .body(String.class);
        return gson.fromJson(jsonResponse, new TypeToken<>() {});
    }
}
