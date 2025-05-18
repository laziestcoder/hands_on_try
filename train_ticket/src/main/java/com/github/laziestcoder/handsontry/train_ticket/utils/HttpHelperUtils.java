package com.github.laziestcoder.handsontry.train_ticket.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static com.github.laziestcoder.handsontry.train_ticket.constant.ApiUrlConstants.RAILWAY_BASE_URL;


/**
 * @author TOWFIQUL ISLAM
 * @since 9/12/24
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class HttpHelperUtils {
    private final RestTemplate restTemplate;

    public ResponseEntity<String> getStringResponseFromGet(String url, String token) {
        HttpHeaders headers = getHttpHeaders(token);
        return getExchange(url, HttpMethod.GET, new HttpEntity<>(headers));
    }

    public ResponseEntity<String> getStringResponseFromPost(String url,
                                                            String payload,
                                                            String token) {
        HttpHeaders headers = getHttpHeaders(token);
        return getExchange(url, HttpMethod.POST, new HttpEntity<>(payload, headers));

    }

    public ResponseEntity<String> getStringResponseFromPatch(String url,
                                                             String payload,
                                                             String token) {
        HttpHeaders headers = getHttpHeaders(token);
        return getExchange(url, HttpMethod.PATCH, new HttpEntity<>(payload, headers));

    }

    private ResponseEntity<String> getExchange(String url,
                                               HttpMethod httpMethod,
                                               HttpEntity<String> httpEntity) {

        ResponseEntity<String> response;
        String logMsgFormat = "url: {}, response: {}, http: {}";
        try {
            response = restTemplate
                    .exchange(url,
                            httpMethod,
                            httpEntity,
                            String.class
                    );

            log.info(logMsgFormat, url, response.getStatusCode(), httpMethod);

        } catch (HttpClientErrorException e) {
            log.error(logMsgFormat, url, e.getStatusCode(), httpMethod);
            response = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        } catch (Exception e) {
            log.error(logMsgFormat, url, e.getMessage(), httpMethod);
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (response.getStatusCode().value() == 401) {
            log.warn(logMsgFormat, url, response.getStatusCode(), httpMethod);
        }

        log.info("response: {}", response.getBody());

        return response;

    }

    private static HttpHeaders getHttpHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("origin", RAILWAY_BASE_URL);
        headers.set("referer", RAILWAY_BASE_URL);
        if (!Objects.isNull(token) && !token.isEmpty()) {
            headers.set("authorization", String.format("Bearer %s", token));
        }
        log.debug("headers: {}", headers);
        return headers;
    }
}


