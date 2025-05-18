package com.github.laziestcoder.handsontry.train_ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author TOWFIQUL ISLAM
 * @since 27/11/24
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardingPoint implements Serializable {
    private Integer trip_point_id;
    private Integer location_id;
    private String location_name;
    private String location_time;
}
