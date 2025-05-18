package com.github.laziestcoder.handsontry.train_ticket.service;

import java.util.Set;

/**
 * @author TOWFIQUL ISLAM
 * @since 10/12/24
 */

public interface TicketIdStoreHandler {
    Set<String> getTicketIds(String key);

    void putTicketId(String key, String ticketId);

    void putTicketIds(String key, Set<String> ticketIds);

    void removeTicketIds(String key);

}
