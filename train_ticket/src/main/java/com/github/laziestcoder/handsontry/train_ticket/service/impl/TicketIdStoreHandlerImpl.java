package com.github.laziestcoder.handsontry.train_ticket.service.impl;

import com.github.laziestcoder.handsontry.train_ticket.service.TicketIdStoreHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author TOWFIQUL ISLAM
 * @since 10/12/24
 */

@Slf4j
@Service
public class TicketIdStoreHandlerImpl implements TicketIdStoreHandler {

    public static final Map<String, Set<String>> RESERVED_TICKET_IDS = new HashMap<>();

    @Override
    public Set<String> getTicketIds(String key) {
        Set<String> existingTicketIds = new HashSet<>();
        if (RESERVED_TICKET_IDS.containsKey(key)) {
            existingTicketIds = RESERVED_TICKET_IDS.get(key);
        }
        return existingTicketIds;
    }

    @Override
    public void putTicketId(String key, String ticketId) {
        Set<String> existingTicketIds = getTicketIds(key);
        removeTicketIds(key);
        existingTicketIds.add(ticketId);
        putTicketIds(key, existingTicketIds);
    }

    @Override
    public void putTicketIds(String key, Set<String> ticketIds) {
        Set<String> existingTicketIds = getTicketIds(key);
        removeTicketIds(key);
        existingTicketIds.addAll(ticketIds);
        RESERVED_TICKET_IDS.put(key, existingTicketIds);
    }

    @Override
    public void removeTicketIds(String key) {
        RESERVED_TICKET_IDS.remove(key);
    }

}
