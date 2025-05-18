package com.github.laziestcoder.handsontry.train_ticket.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author TOWFIQUL ISLAM
 * @since 9/12/24
 */

@Data
public class TicketInfo implements Serializable {
    private String seat_number;
    private String seat_fare;
    private Long route_id;
    private Long ticket_id;
    private String url;
}
