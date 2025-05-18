package com.github.laziestcoder.handsontry.train_ticket.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author TOWFIQUL ISLAM
 * @since 9/12/24
 */

@Data
public class SeatLayout implements Serializable {
    private String floor_name;
    private int seat_floor;
    private int seat_type;
    private int fare_type_id;
    private String seat_fare_type;
    private boolean seat_availability;
    private String seat_fare;
    private List<List<Seat>> layout;
}
