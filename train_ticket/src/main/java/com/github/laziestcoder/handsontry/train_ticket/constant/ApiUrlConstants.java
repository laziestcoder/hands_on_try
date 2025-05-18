package com.github.laziestcoder.handsontry.train_ticket.constant;

/**
 * @author TOWFIQUL ISLAM
 * @since 10/12/24
 */

public class ApiUrlConstants {

    // EXTERNAl
    public static final String RAILWAY_BASE_URL = "https://eticket.railway.gov.bd";
    public static final String RAILWAY_SHOHOZ_API_BASE_URL = "https://railspaapi.shohoz.com";

    public static final String LOGIN_URL = "/v1.0/web/auth/sign-in";
    public static final String SEARCH_TRAIN_URL = "/v1.0/web/bookings/search-trips-v2";
    public static final String SEAT_LAYOUT_URL = "/v1.0/web/bookings/seat-layout";
    public static final String SEAT_RESERVATION_URL = "/v1.0/web/bookings/reserve-seat";

    // INTERNAL
    public static final String RESERVE_TICKET_URL = "/api/booking/pre/reserve/ticket?ticketId=%s&routeId=%s";

}
