package com.github.laziestcoder.handsontry.train_ticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

/**
 * @author TOWFIQUL ISLAM
 * @since 26/11/24
 */

@SpringBootTest
class LoginServiceTest {

    @Autowired
    private LoginService loginService; // Your service class containing the login method
    private RestTemplate restTemplate;

}