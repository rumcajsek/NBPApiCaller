package pl.krol.springtrainer.rest;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import pl.krol.springtrainer.annotations.ValueValidator;
import pl.krol.springtrainer.exceptions.ApiRestCallException;
import pl.krol.springtrainer.exceptions.RateListRequestedButCountIsNullException;
import pl.krol.springtrainer.exceptions.UnallowedFieldValueException;
import pl.krol.springtrainer.objects.ATableAllCurrenciesObject;
import pl.krol.springtrainer.objects.ATableCurrencyObject;
import pl.krol.springtrainer.objects.SingleCurrencyParameters;

import java.lang.reflect.Type;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class ApiRestClient {
    private final String GENERIC_EXCHANGE_RATE_API_ADDRESS = "http://api.nbp.pl/api/exchangerates/rates/";
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
                .uri(URI.create(GENERIC_EXCHANGE_RATE_API_ADDRESS + "A/" + currencyCode + "/"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    throw new ApiRestCallException(response);
                })
                .body(ATableCurrencyObject.class);
    }

    public ATableCurrencyObject getAnyCurrencyOnSpecificDateReturnATable(String currencyCode, LocalDate date) {
        return restClient.get()
                .uri(URI.create(GENERIC_EXCHANGE_RATE_API_ADDRESS + "A/" + currencyCode + "/" + date + "/"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    throw new ApiRestCallException(response);
                })
                .body(ATableCurrencyObject.class);
    }

    public ATableCurrencyObject alternativeRestCallWithBuilderParameter(SingleCurrencyParameters param) throws UnallowedFieldValueException, IllegalAccessException {
        URI uri = generateURI(param);
        System.out.println(uri);
        return null;
//        return restClient.get()
//                .uri(generateURI(param))
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .onStatus(HttpStatusCode::isError, (request, response) -> {
//                    throw new ApiRestCallException(response);
//                })
//                .body(ATableCurrencyObject.class);
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

    private URI generateURI(SingleCurrencyParameters param) throws UnallowedFieldValueException, IllegalAccessException {
        ValueValidator.validate(param);
        StringBuilder uri = new StringBuilder(GENERIC_EXCHANGE_RATE_API_ADDRESS);
        uri.append(param.getTable()).append("/");
        uri.append(param.getCode()).append("/");
        if(param.getLastOrToday() != null && !param.getLastOrToday().isEmpty()) {
            uri.append(param.getLastOrToday()).append("/");
            if(param.getLastOrToday().equals("last")) {
                if (param.getTopCount() == null) {
                    throw new RateListRequestedButCountIsNullException();
                }
                uri.append(param.getTopCount()).append("/");
            }
            return URI.create(uri.toString());
        }
        if(param.getStartDate() != null) {
            uri.append(param.getStartDate().toString()).append("/");
        }
        if(param.getStartDate() != null) {
            uri.append(param.getStartDate().toString()).append("/");
        }
        return URI.create(uri.toString());
    }
}
