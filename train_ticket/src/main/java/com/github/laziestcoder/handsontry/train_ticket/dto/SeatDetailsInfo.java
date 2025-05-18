package com.github.laziestcoder.handsontry.train_ticket.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author TOWFIQUL ISLAM
 * @since 9/12/24
 */

@Data
public class SeatDetailsInfo implements Serializable {
    private List<SeatLayout> seatLayout;
    private int totalSeats;
    private int reservationFee;
    private String reservationFeeUnit;
}
