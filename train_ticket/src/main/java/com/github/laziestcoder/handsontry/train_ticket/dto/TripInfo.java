package com.github.laziestcoder.handsontry.train_ticket.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author TOWFIQUL ISLAM
 * @since 9/12/24
 */

@Data
public class TripInfo implements Serializable {
    private String train_id;
    private String seat_class;
    private String journey_date;
    private String from;
    private String to;
    private RouteInfo trip_info;
}
