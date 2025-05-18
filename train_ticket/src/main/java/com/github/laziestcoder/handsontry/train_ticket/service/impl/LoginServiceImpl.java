package com.github.laziestcoder.handsontry.train_ticket.service.impl;

import com.github.laziestcoder.handsontry.train_ticket.dto.LoginRequest;
import com.github.laziestcoder.handsontry.train_ticket.service.LoginService;
import com.github.laziestcoder.handsontry.train_ticket.utils.HttpHelperUtils;
import com.github.laziestcoder.handsontry.train_ticket.utils.JSONHelper;
import com.github.laziestcoder.handsontry.train_ticket.utils.TokenStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import static com.github.laziestcoder.handsontry.train_ticket.constant.ApiUrlConstants.LOGIN_URL;
import static com.github.laziestcoder.handsontry.train_ticket.constant.RailwayValues.*;


/**
 * @author TOWFIQUL ISLAM
 * @since 26/11/24
 */

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final HttpHelperUtils helperUtils;

    @Value("${railway.api.base-url}")
    private String railwayApiBaseUrl;

    @Value("${railway.api.username}")
    private String username;

    @Value("${railway.api.password}")
    private String password;

    @Override
    public String login() {
        LoginRequest loginRequest = new LoginRequest(username, password);
        return getToken(loginRequest);
    }

    @Override
    public String getToken() {
        return login();
    }

    @Override
    public String login(LoginRequest loginRequest) {
        return getToken(loginRequest);
    }

    @Override
    public void invalidateLogin() {
        TokenStore.clear();
    }

    private String getToken(LoginRequest loginRequest) {
        String url = UriComponentsBuilder.fromUriString(railwayApiBaseUrl + LOGIN_URL).toUriString();
        String payload = String.format("{\"%s\":\"%s\",\"%s\":\"%s\"}",
                QUERY_PARAM_MOBILE_NUMBER,
                loginRequest.getMobile_number(),
                QUERY_PARAM_PASSWORD,
                loginRequest.getPassword()
        );

        String token = TokenStore.getToken();
        if (!token.isEmpty()) return token;

        ResponseEntity<String> response = helperUtils.getStringResponseFromPost(url, payload, "");

        token = JSONHelper.extractValueFromResponseData(response.getBody(), DATA_KEY, TOKEN_KEY);

        TokenStore.setToken(token, 30);

        return token;
    }

}
