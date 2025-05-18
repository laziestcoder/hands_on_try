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
public class User implements Serializable {
    private String passport;
    private boolean isSubmittedForManualVerification;
    private boolean isEmailVerificationRequired;
    private int isEmailVerified;
    private int nidValidated;
    private boolean isNidVerificationRequired;
}
