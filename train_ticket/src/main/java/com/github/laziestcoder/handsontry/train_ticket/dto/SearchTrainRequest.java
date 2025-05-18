package com.github.laziestcoder.handsontry.train_ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author TOWFIQUL ISLAM
 * @since 27/11/24
 */

@Data
@AllArgsConstructor
public class SearchTrainRequest implements Serializable {
    private String fromCity;
    private String toCity;
    private String journeyDate;
    private String seatClass;
    private String trainId;
}
