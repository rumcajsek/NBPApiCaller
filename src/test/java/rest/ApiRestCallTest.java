package rest;


import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import pl.krol.springtrainer.exceptions.UnallowedFieldValueException;
import pl.krol.springtrainer.exceptions.UnallowedNullFieldException;
import pl.krol.springtrainer.objects.SingleCurrencyParameters;
import pl.krol.springtrainer.rest.ApiRestClient;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.net.URI;
import java.time.LocalDate;

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
}