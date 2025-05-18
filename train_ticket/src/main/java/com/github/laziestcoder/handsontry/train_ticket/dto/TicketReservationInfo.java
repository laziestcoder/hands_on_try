package com.github.laziestcoder.handsontry.train_ticket.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author TOWFIQUL ISLAM
 * @since 9/12/24
 */

@Data
public class TicketReservationInfo implements Serializable {
    protected Integer ticketCount;
    protected List<TicketInfo> ticketInfos;
}
