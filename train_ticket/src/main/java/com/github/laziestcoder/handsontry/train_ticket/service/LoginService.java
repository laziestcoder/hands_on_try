package com.github.laziestcoder.handsontry.train_ticket.service;

import com.github.laziestcoder.handsontry.train_ticket.dto.LoginRequest;

/**
 * @author TOWFIQUL ISLAM
 * @since 26/11/24
 */

public interface LoginService {
    String login();

    String login(LoginRequest loginRequest);

    String getToken();

    void invalidateLogin();
}
