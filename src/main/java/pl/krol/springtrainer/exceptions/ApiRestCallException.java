package pl.krol.springtrainer.exceptions;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestClientException;

import java.io.IOException;

public class ApiRestCallException extends RestClientException {
    public ApiRestCallException(ClientHttpResponse response) throws IOException {
        super(response.getStatusCode() + " " + response.getStatusText());
    }

    public ApiRestCallException(String msg) {
        super(msg);
    }

    public ApiRestCallException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
