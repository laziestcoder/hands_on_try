package com.github.laziestcoder.handsontry.train_ticket.controller;

import com.github.laziestcoder.handsontry.train_ticket.service.RailwayTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author TOWFIQUL ISLAM
 * @since 26/11/24
 */

@RequiredArgsConstructor
@RequestMapping("/api/booking")
@RestController
public class BookingController {
    private final RailwayTicketService railwayTicketService;

    @GetMapping("/pre/reserve/ticket")
    public Map<String, Object> preReserveTicket(@RequestParam String ticketId, @RequestParam String routeId) {
        return railwayTicketService.preReserveTicket(ticketId, routeId);
    }

    @GetMapping("/reserve/ticket")
    public Map<String, Object> reserveTicket(@RequestParam String routeId) {
        return railwayTicketService.reserveTickets(routeId);
    }
}
