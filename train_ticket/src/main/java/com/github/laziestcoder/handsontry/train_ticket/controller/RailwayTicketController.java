package com.github.laziestcoder.handsontry.train_ticket.controller;

import com.github.laziestcoder.handsontry.train_ticket.dto.SearchTrainRequest;
import com.github.laziestcoder.handsontry.train_ticket.dto.TicketReservationInfo;
import com.github.laziestcoder.handsontry.train_ticket.dto.TripInfo;
import com.github.laziestcoder.handsontry.train_ticket.service.RailwayTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author TOWFIQUL ISLAM
 * @since 26/11/24
 */

@RequiredArgsConstructor
@RequestMapping("/api/railway")
@RestController
public class RailwayTicketController {

    private final RailwayTicketService railwayTicketService;

    @GetMapping(value = "/search/train", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TripInfo> searchTrains(@RequestParam String fromCity,
                                                 @RequestParam String toCity,
                                                 @RequestParam String journeyDate,
                                                 @RequestParam String seatClass,
                                                 @RequestParam String trainId) {

        TripInfo data = railwayTicketService.getTripInfo(new SearchTrainRequest(fromCity, toCity, journeyDate, seatClass, trainId));
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping(value = "/seat/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TicketReservationInfo> getSeatInfo(@RequestParam String tripId, @RequestParam String tripRouteId) {

        TicketReservationInfo data = railwayTicketService.getSeatInformation(tripId, tripRouteId);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/train/available")
    public Map<String, Object> isTrainAvailable(@RequestParam String fromCity,
                                                @RequestParam String toCity,
                                                @RequestParam String journeyDate,
                                                @RequestParam String seatClass,
                                                @RequestParam String trainId) {

        return railwayTicketService.getAvailableTrain(new SearchTrainRequest(fromCity, toCity, journeyDate, seatClass, trainId));
    }
}
