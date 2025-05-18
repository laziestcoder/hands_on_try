package com.github.laziestcoder.handsontry.train_ticket.service;

import com.github.laziestcoder.handsontry.train_ticket.dto.SearchTrainRequest;
import com.github.laziestcoder.handsontry.train_ticket.dto.TicketReservationInfo;
import com.github.laziestcoder.handsontry.train_ticket.dto.TripInfo;

import java.util.Map;

/**
 * @author TOWFIQUL ISLAM
 * @since 26/11/24
 */

public interface RailwayTicketService {
    String searchTrains(SearchTrainRequest searchTrainRequest);

    TripInfo getTripInfo(SearchTrainRequest searchTrainRequest);

    Boolean isTripAvailable(String responseBody, String trainId);

    Map<String, Object> getAvailableTrain(SearchTrainRequest searchTrainRequest);

    TicketReservationInfo getSeatInformation(String tripId, String tripRouteId);

    Map<String, Object> preReserveTicket(String ticketId, String routeId);

    Map<String, Object> reserveTickets(String routeId);
}
