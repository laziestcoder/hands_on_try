package com.github.laziestcoder.handsontry.train_ticket.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author TOWFIQUL ISLAM
 * @since 26/11/24
 */

@Data
public class SeatCounts implements Serializable {
    private Integer online;
    private Integer offline;
    private Boolean is_divided;
}
