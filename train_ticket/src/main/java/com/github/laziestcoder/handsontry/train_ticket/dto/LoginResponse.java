package com.github.laziestcoder.handsontry.train_ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author TOWFIQUL ISLAM
 * @since 26/11/24
 */

@Data
@AllArgsConstructor
public class LoginResponse implements Serializable {
    private String message;
    private int nidValidated;
    private String token;
    private User user;
}
