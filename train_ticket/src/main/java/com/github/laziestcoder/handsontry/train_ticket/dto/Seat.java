package com.github.laziestcoder.handsontry.train_ticket.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author TOWFIQUL ISLAM
 * @since 9/12/24
 */

@Data
public class Seat implements Serializable {
    private boolean isHidden;
    private int seat_availability;
    private String seat_number;
    private long ticket_id;
    private int ticket_type;
}
