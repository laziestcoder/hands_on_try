package com.github.laziestcoder.handsontry.train_ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author TOWFIQUL ISLAM
 * @since 26/11/24
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainDetails implements Serializable {
    private String trip_number;
    private String departure_date_time;
    private String departure_full_date;
    private String departure_date_time_jd;
    private String arrival_date_time;
    private String travel_time;
    private String origin_city_name;
    private String destination_city_name;
    private List<SeatType> seat_types;
    private String train_model;
    private Boolean is_open_for_all;
    private Integer is_eid_trip;
    private List<BoardingPoint> boarding_points;
    private Integer is_international;
    private Boolean is_from_city_international;
}
