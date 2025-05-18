package com.github.laziestcoder.handsontry.train_ticket.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author TOWFIQUL ISLAM
 * @since 9/12/24
 */

@Data
public class RouteInfo implements Serializable {
    private Integer trip_id;
    private Integer route_id;
    private Integer seat_count;
    private Integer trip_route_id;
}
