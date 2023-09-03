package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Slf4j
public class CustomRestTemplate extends RestTemplate {


    public CustomRestTemplate() {
        super();
        initialize();
    }

    public CustomRestTemplate(ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
        initialize();
    }

    private void initialize() {
        setErrorHandler(new CustomResponseErrorHandler());
    }

    private static class CustomResponseErrorHandler implements ResponseErrorHandler {
        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return !response.getStatusCode().is2xxSuccessful();
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            log.info("Can't handle response, status code: {}", response.getStatusCode());
        }
    }
}
