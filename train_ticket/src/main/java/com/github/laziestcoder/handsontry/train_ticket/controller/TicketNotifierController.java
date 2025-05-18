package com.github.laziestcoder.handsontry.train_ticket.controller;

import com.github.laziestcoder.handsontry.train_ticket.service.TicketNotifier;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TOWFIQUL ISLAM
 * @since 26/11/24
 */

@RequestMapping("/api/ticket/notifier")
@RequiredArgsConstructor
@RestController
public class TicketNotifierController {
    private final TicketNotifier ticketNotifier;

    @GetMapping("/dhk-to-ctg")
    public void dhkToCtg() {
        ticketNotifier.checkTicketAvailabilityForDhkToCtg();
    }
}
