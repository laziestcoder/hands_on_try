package com.github.laziestcoder.handsontry.train_ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author TOWFIQUL ISLAM
 * @since 26/11/24
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatType implements Serializable {
    private Integer key;
    private String type;
    private Integer trip_id;
    private Integer trip_route_id;
    private Integer route_id;
    private String fare;
    private Integer vat_percent;
    private Integer vat_amount;
    private Integer origin_city_seq;
    private Integer destination_city_seq;
    private SeatCounts seat_counts;
}
