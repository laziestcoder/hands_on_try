package com.github.laziestcoder.handsontry.train_ticket.service;

/**
 * @author TOWFIQUL ISLAM
 * @since 26/11/24
 */

public interface EmailService {
    void sendEmail(String to, String subject, String text);
}
