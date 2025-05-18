package com.github.laziestcoder.handsontry.train_ticket.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.laziestcoder.handsontry.train_ticket.dto.*;
import com.github.laziestcoder.handsontry.train_ticket.service.LoginService;
import com.github.laziestcoder.handsontry.train_ticket.service.RailwayTicketService;
import com.github.laziestcoder.handsontry.train_ticket.service.TicketIdStoreHandler;
import com.github.laziestcoder.handsontry.train_ticket.utils.HttpHelperUtils;
import com.github.laziestcoder.handsontry.train_ticket.utils.JSONHelper;
import com.github.laziestcoder.handsontry.train_ticket.utils.ObjectMapperUtils;
import com.github.laziestcoder.handsontry.train_ticket.utils.TokenStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.github.laziestcoder.handsontry.train_ticket.constant.ApiUrlConstants.*;
import static com.github.laziestcoder.handsontry.train_ticket.constant.RailwayValues.*;


/**
 * @author TOWFIQUL ISLAM
 * @since 26/11/24
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class RailwayTicketServiceImpl implements RailwayTicketService {

    private int RETRY_COUNTER_FOR_AVAILABLE_TRIP = 0;
    private final TicketIdStoreHandler ticketIdStoreHandler;

    private final ObjectMapperUtils objectMapperUtils;
    private final HttpHelperUtils helperUtils;
    private final LoginService loginService;

    @Value("${railway.api.base-url}")
    private String railwayApiBaseUrl;

    @Override
    public String searchTrains(SearchTrainRequest searchTrainRequest) {
        String url = UriComponentsBuilder.fromUriString(railwayApiBaseUrl + SEARCH_TRAIN_URL)
                .queryParam(QUERY_PARAM_FROM_CITY, searchTrainRequest.getFromCity())
                .queryParam(QUERY_PARAM_TO_CITY, searchTrainRequest.getToCity())
                .queryParam(QUERY_PARAM_DATE_OF_JOURNEY, searchTrainRequest.getJourneyDate())
                .queryParam(QUERY_PARAM_SEAT_CLASS, searchTrainRequest.getSeatClass())
                .toUriString();

        String trainId = searchTrainRequest.getTrainId();
        log.debug("bookings search-trips url: {}", url);

        ResponseEntity<String> response = helperUtils.getStringResponseFromGet(url, "");

        String responseBody = response.getBody();

        Boolean isTrainAvailable = JSONHelper
                .checkAvailableValueFromResponseDataList(response.getBody(), DATA_KEY, TRAINS_KEY, TRAIN_MODEL_KEY, trainId);

        log.info("trainModel {} available: {}", trainId, isTrainAvailable);

        return responseBody;
    }

    @Override
    public Boolean isTripAvailable(String responseBody, String trainId) {
        boolean isTrainExist = JSONHelper.checkAvailableValueFromResponseDataList(
                responseBody, DATA_KEY, TRAINS_KEY, TRAIN_MODEL_KEY, trainId);
        log.info("isTrainExist {}", isTrainExist);
        boolean isSeatAvailable = false;
        if (isTrainExist) {
            String train = JSONHelper.extractValueFromResponseDataList(
                    responseBody, DATA_KEY, TRAINS_KEY, TRAIN_MODEL_KEY, trainId);
            log.info("train {}", train);
            String seatTypes = JSONHelper.extractValueFromResponseData(train, SEAT_TYPES_KEY, TYPE_KEY, SNIGDHA);
            log.info("seatTypes {}", seatTypes);
            String seatCountsJson = JSONHelper.extractJsonFromResponseData(seatTypes, SEAT_COUNTS_KEY);
            log.info("seatCountsJson {}", seatCountsJson);
            SeatCounts seatCounts = JSONHelper.convertJsonStringToObjectValue(seatCountsJson, SeatCounts.class);
            log.info("seatCounts {}", seatCounts);
            isSeatAvailable = seatCounts.getOnline() > 0;
        }
        log.info("result : isTrainExist {} - isSeatAvailable {}", isTrainExist, isSeatAvailable);

        return isTrainExist && isSeatAvailable;
    }

    @Override
    public TripInfo getTripInfo(SearchTrainRequest searchTrainRequest) {
        String responseBody = searchTrains(searchTrainRequest);
        boolean isTripAvailable = isTripAvailable(responseBody, searchTrainRequest.getTrainId());
        if (isTripAvailable) {
            log.info("ticket found for {}", searchTrainRequest);
            return generateTripInfo(responseBody, searchTrainRequest);
        }
        return new TripInfo();
    }

    @Override
    public Map<String, Object> preReserveTicket(String ticketId, String routeId) {
        ticketIdStoreHandler.putTicketId(routeId, ticketId);
        return Map.of(
                "success", true,
                "message", String.format("Ticket pre-reserved for ticketId %s and routeId %s", ticketId, routeId)
        );
    }

    @Override
    public Map<String, Object> reserveTickets(String routeId) {
        Set<String> preservedTicketIds = ticketIdStoreHandler.getTicketIds(routeId);
        List<Map<String, Object>> response = new ArrayList<>();
        for (String ticketId : preservedTicketIds) {
            response.add(confirmReserveTicket(ticketId, routeId));
        }
        ticketIdStoreHandler.removeTicketIds(routeId);
        return Map.of("message", response);
    }

    @Override
    public Map<String, Object> getAvailableTrain(SearchTrainRequest searchTrainRequest) {
        String responseBody = searchTrains(searchTrainRequest);
        boolean isTrainExist = JSONHelper.checkAvailableValueFromResponseDataList(responseBody, DATA_KEY, TRAINS_KEY, TRAIN_MODEL_KEY, searchTrainRequest.getTrainId());
        boolean isSeatAvailable = false;
        if (isTrainExist) {
            String train = JSONHelper.extractValueFromResponseDataList(
                    responseBody, DATA_KEY, TRAINS_KEY, TRAIN_MODEL_KEY, searchTrainRequest.getTrainId());
            log.debug("train {}", train);
            String seatTypes = JSONHelper.extractValueFromResponseData(train, SEAT_TYPES_KEY, TYPE_KEY, SNIGDHA);
            log.debug("seatTypes {}", seatTypes);
            String seatCountsJson = JSONHelper.extractJsonFromResponseData(seatTypes, SEAT_COUNTS_KEY);
            log.debug("seatCountsJson {}", seatCountsJson);
            SeatCounts seatCounts = JSONHelper.convertJsonStringToObjectValue(seatCountsJson, SeatCounts.class);
            log.debug("seatCounts {}", seatCounts);
            isSeatAvailable = seatCounts.getOnline() + seatCounts.getOffline() > 0;
        }

        log.info("result : isTrainExist {} - isSeatAvailable {}", isTrainExist, isSeatAvailable);
        if (!(isTrainExist && isSeatAvailable) && RETRY_COUNTER_FOR_AVAILABLE_TRIP < 3) {
            RETRY_COUNTER_FOR_AVAILABLE_TRIP++;
            getAvailableTrain(searchTrainRequest);
        }
        RETRY_COUNTER_FOR_AVAILABLE_TRIP = 0;

        String result = isTrainExist && isSeatAvailable ? "available" : "not available";
        boolean verdict = isTrainExist && isSeatAvailable;
        return Map.of("message", String.format("Train is %s", result), "success", verdict);
    }

    @Override
    public TicketReservationInfo getSeatInformation(String tripId, String tripRouteId) {

        SeatDetailsInfo seatDetailsInfo = getSeatDetailsInfo(tripId, tripRouteId);
        TicketReservationInfo ticketReservationInfo = getTicketReservationInfo(seatDetailsInfo, tripRouteId);

        log.debug("ticketReservation: {}", ticketReservationInfo);

        return ticketReservationInfo;
    }

    private TripInfo generateTripInfo(String responseBody, SearchTrainRequest searchTrainRequest) {
        TripInfo tripInfo = new TripInfo();
        tripInfo.setTrain_id(searchTrainRequest.getTrainId());
        tripInfo.setFrom(searchTrainRequest.getFromCity());
        tripInfo.setTo(searchTrainRequest.getToCity());
        tripInfo.setSeat_class(searchTrainRequest.getSeatClass());
        tripInfo.setJourney_date(searchTrainRequest.getJourneyDate());
        TrainDetails trainDetails = getTrainDetails(responseBody, searchTrainRequest.getTrainId());
        tripInfo.setTrip_info(getTripInfo(trainDetails.getSeat_types(), searchTrainRequest.getSeatClass()));
        return tripInfo;
    }

    private RouteInfo getTripInfo(List<SeatType> seatTypes, String seatClass) {
        RouteInfo routeInfo = new RouteInfo();
        AtomicReference<Integer> seatCount = new AtomicReference<>(0);
        seatTypes.forEach(seatType -> {
            if (seatType.getType().equalsIgnoreCase(seatClass)) {
                log.info("seatTypes {}", seatType);
                routeInfo.setTrip_id(seatType.getTrip_id());
                routeInfo.setTrip_route_id(seatType.getTrip_route_id());
                routeInfo.setRoute_id(seatType.getRoute_id());
                seatCount.getAndSet(seatCount.get() +
                        seatType.getSeat_counts().getOnline() + seatType.getSeat_counts().getOffline());
            }
        });

        routeInfo.setSeat_count(seatCount.get());
        return routeInfo;
    }

    private TrainDetails getTrainDetails(String responseBody, String trainId) {
        String train = JSONHelper.extractValueFromResponseDataList(
                responseBody, DATA_KEY, TRAINS_KEY, TRAIN_MODEL_KEY, trainId);
        JsonNode trainJson = objectMapperUtils.convertStringToJson(train);
        TrainDetails trainDetails = objectMapperUtils.convertToType(trainJson, TrainDetails.class);
        log.info("trainDetails {}", trainDetails);
        return trainDetails;

    }

    private TicketReservationInfo getTicketReservationInfo(SeatDetailsInfo seatDetailsInfo, String tripRouteId) {
        log.debug("getTicketReservation {}, routeId: {}", seatDetailsInfo, tripRouteId);
        TicketReservationInfo ticketReservationInfo = new TicketReservationInfo();
        List<TicketInfo> ticketInfos = new ArrayList<>();
        for (SeatLayout seatLayout : seatDetailsInfo.getSeatLayout()) {
            seatLayout.getLayout().forEach(layout -> {
                for (Seat seat : layout) {
                    log.debug("seat {}", seat);
                    TicketInfo ticketInfo = new TicketInfo();
                    ticketInfo.setSeat_number(seat.getSeat_number());
                    ticketInfo.setSeat_fare(seatLayout.getSeat_fare());
                    ticketInfo.setTicket_id(seat.getTicket_id());
                    ticketInfo.setRoute_id(Long.valueOf(tripRouteId));
                    String url = String.format(RESERVE_TICKET_URL, ticketInfo.getTicket_id(), ticketInfo.getRoute_id());
                    ticketInfo.setUrl(UriComponentsBuilder.fromUriString(url).build().toUriString());
                    ticketInfos.add(ticketInfo);
                    log.debug("routeId: {}, ticketId: {}", ticketInfo.getRoute_id(), ticketInfo.getTicket_id());
                }
            });
        }
        ticketReservationInfo.setTicketInfos(ticketInfos);
        ticketReservationInfo.setTicketCount(seatDetailsInfo.getTotalSeats());
        ticketReservationInfo.getTicketInfos().sort(Comparator.comparing(TicketInfo::getSeat_number));
        log.info("ticketReservation: {}", ticketReservationInfo.getTicketCount());

        return ticketReservationInfo;
    }

    private SeatDetailsInfo getSeatDetailsInfo(String tripId, String tripRouteId) {
        String url = UriComponentsBuilder.fromUriString(railwayApiBaseUrl + SEAT_LAYOUT_URL)
                .queryParam(QUERY_PARAM_TRIP_ID, tripId)
                .queryParam(QUERY_PARAM_TRIP_ROUTE_ID, tripRouteId)
                .toUriString();

        log.debug("seat layout url: {}", url);

        ResponseEntity<String> responseString = helperUtils.getStringResponseFromGet(url, loginService.getToken());

        JsonNode responseJson = objectMapperUtils.convertStringToJson(responseString.getBody());
        SeatDetailsInfo seatDetailsInfo = objectMapperUtils.convertToType(responseJson.get(DATA_KEY), SeatDetailsInfo.class);
        seatDetailsInfo.getSeatLayout().forEach(seatLayout -> {
            List<List<Seat>> filteredLayout = new java.util.ArrayList<>(seatLayout.getLayout().stream()
                    .map(seats -> seats.stream()
                            .filter(seat -> seat.getSeat_availability() != 0)
                            .toList()
                    )
                    .toList());
            seatLayout.setLayout(filteredLayout);
            seatLayout.getLayout().removeIf(List::isEmpty);
        });
        seatDetailsInfo.setSeatLayout(
                seatDetailsInfo.getSeatLayout().stream()
                        .filter(seatLayout -> !seatLayout.getLayout().isEmpty())
                        .toList()
        );

        seatDetailsInfo.setTotalSeats(
                seatDetailsInfo.getSeatLayout().stream()
                        .mapToInt(seatLayout -> (int) seatLayout.getLayout().stream()
                                .flatMap(List::stream)
                                .filter(seat -> seat.getSeat_availability() == 1)
                                .count()
                        )
                        .sum()
        );
        log.debug("bookings seat-layout response: {}", seatDetailsInfo);
        return seatDetailsInfo;
    }

    private Map<String, Object> confirmReserveTicket(String ticketId, String routeId) {
        String url = UriComponentsBuilder.fromUriString(railwayApiBaseUrl + SEAT_RESERVATION_URL).toUriString();
        String payload = String.format("{\"%s\":\"%s\", \"%s\":\"%s\"}",
                QUERY_PARAM_TICKET_ID, ticketId, QUERY_PARAM_ROUTE_ID, routeId);

        log.debug("seat reservation url: {}", url);

        ResponseEntity<String> response = helperUtils.getStringResponseFromPatch(url, payload, TokenStore.getToken());
        boolean result = response.getStatusCode().is2xxSuccessful();
        return Map.of("ticketId", ticketId,
                "routeId", routeId,
                "success", result,
                "message", Objects.isNull(response.getBody()) ? "" : response.getBody()
        );
    }
}