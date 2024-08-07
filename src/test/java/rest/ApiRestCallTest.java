package rest;


import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import pl.krol.springtrainer.exceptions.RateListRequestedButCountIsNullException;
import pl.krol.springtrainer.exceptions.UnallowedFieldValueException;
import pl.krol.springtrainer.exceptions.UnallowedNullFieldException;
import pl.krol.springtrainer.objects.SingleCurrencyParameters;
import pl.krol.springtrainer.rest.ApiRestClient;

import java.net.URI;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ApiRestCallTest {

    private static final String RATES_API_ADDRESS = "http://api.nbp.pl/api/exchangerates/rates/";

    private final ApiRestClient apiRestClient = new ApiRestClient();

    @Test
    public void testAPIAddressBuilderWithoutTable() {
        // given
        SingleCurrencyParameters params = new SingleCurrencyParameters.Builder()
                .code("EUR")
                .lastOrToday("last")
                .topCount(3L)
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 5, 31))
                .build();

        // when

        // then // expect
        assertThatExceptionOfType(UnallowedNullFieldException.class)
                .isThrownBy(() -> apiRestClient.glueURIparams(params))
                .withMessage("Field 'table' cannot be a null value")
                .withNoCause();
    }

    @Test
    public void testAPIAddressBuilderWithoutCode() {
        // given
        SingleCurrencyParameters params = new SingleCurrencyParameters.Builder()
                .table("A")
                .lastOrToday("last")
                .topCount(3L)
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 5, 31))
                .build();

        // when

        // then // expect
        assertThatExceptionOfType(UnallowedNullFieldException.class)
                .isThrownBy(() -> apiRestClient.glueURIparams(params))
                .withMessage("Field 'code' cannot be a null value")
                .withNoCause();
    }

    @Test
    public void testAPIAddressBuilderWithWrongTable() {
        // given
        SingleCurrencyParameters params = new SingleCurrencyParameters.Builder()
                .table("D")
                .code("EUR")
                .lastOrToday("last")
                .topCount(3L)
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 5, 31))
                .build();

        // when

        // then // expect
        assertThatExceptionOfType(UnallowedFieldValueException.class)
                .isThrownBy(() -> apiRestClient.glueURIparams(params))
                .withMessage("Field 'table' contains unallowed values.")
                .withNoCause();
    }

    @Test
    public void testAPIAddressBuilderWithWrongCurrency() {
        // given
        SingleCurrencyParameters params = new SingleCurrencyParameters.Builder()
                .table("A")
                .code("ABC")
                .lastOrToday("last")
                .topCount(3L)
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 5, 31))
                .build();

        // when

        // then // expect
        assertThatExceptionOfType(UnallowedFieldValueException.class)
                .isThrownBy(() -> apiRestClient.glueURIparams(params))
                .withMessage("Field 'code' contains unallowed values.")
                .withNoCause();
    }

    @Test
    public void testAPIAddressBuilderWithWrongLastOrToday() {
        // given
        SingleCurrencyParameters params = new SingleCurrencyParameters.Builder()
                .table("A")
                .code("USD")
                .lastOrToday("last_christmas_agavumaha")
                .topCount(3L)
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 5, 31))
                .build();

        // when

        // then // expect
        assertThatExceptionOfType(UnallowedFieldValueException.class)
                .isThrownBy(() -> apiRestClient.glueURIparams(params))
                .withMessage("Field 'lastOrToday' contains unallowed values.")
                .withNoCause();
    }

    @Test
    public void testAPIAddressBuilderWithLastButNoTopCount() {
        // given
        SingleCurrencyParameters params = new SingleCurrencyParameters.Builder()
                .table("A")
                .code("USD")
                .lastOrToday("last")
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 5, 31))
                .build();

        // when

        // then // expect
        assertThatExceptionOfType(RateListRequestedButCountIsNullException.class)
                .isThrownBy(() -> apiRestClient.glueURIparams(params))
                .withMessage("Parameter \"last\" chosen but \"topCount\" is null")
                .withNoCause();
    }

    @Test
    @SneakyThrows
    public void testAPIAddressBuilderWithAllCorrectParameters() {
        // given
        SingleCurrencyParameters params = new SingleCurrencyParameters.Builder()
                .table("A")
                .code("USD")
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 5, 31))
                .build();

        // when
        URI testURI = apiRestClient.glueURIparams(params);

        // then // expect
        assertThat(testURI).isEqualTo(URI.create("http://api.nbp.pl/api/exchangerates/rates/A/USD/2024-01-01/2024-05-31/"));
    }

    @Test
    @SneakyThrows
    public void testAPIAddressBuilderWithTodayParameterURIShouldNotHaveTopCountOrDates() {
        // given
        SingleCurrencyParameters params = new SingleCurrencyParameters.Builder()
                .table("A")
                .code("USD")
                .lastOrToday("today")
                .topCount(10L)
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 5, 31))
                .build();

        // when
        URI testURI = apiRestClient.glueURIparams(params);

        // then // expect
        assertThat(testURI).isEqualTo(URI.create("http://api.nbp.pl/api/exchangerates/rates/A/USD/today/"));
    }
}