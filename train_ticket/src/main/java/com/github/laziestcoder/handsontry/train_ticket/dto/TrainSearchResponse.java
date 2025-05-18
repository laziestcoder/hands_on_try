package com.github.laziestcoder.handsontry.train_ticket.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author TOWFIQUL ISLAM
 * @since 26/11/24
 */

@Data
public class TrainSearchResponse implements Serializable {
    private List<TrainDetails> trains;
    private List<Object> recommendations;
    private String selected_seat_class;
}
