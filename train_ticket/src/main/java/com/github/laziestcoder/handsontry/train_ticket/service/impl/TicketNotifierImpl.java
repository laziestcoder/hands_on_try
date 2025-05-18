package com.github.laziestcoder.handsontry.train_ticket.service.impl;

import com.github.laziestcoder.handsontry.train_ticket.dto.SearchTrainRequest;
import com.github.laziestcoder.handsontry.train_ticket.service.EmailService;
import com.github.laziestcoder.handsontry.train_ticket.service.RailwayTicketService;
import com.github.laziestcoder.handsontry.train_ticket.service.TicketNotifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static com.github.laziestcoder.handsontry.train_ticket.constant.RailwayValues.*;

/**
 * @author TOWFIQUL ISLAM
 * @since 26/11/24
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketNotifierImpl implements TicketNotifier {
    private final RailwayTicketService railwayTicketService;
    private final EmailService emailService;

    @Value("${ticket.for,future.in.days}")
    private int days;
    @Value("${train.id.ctg.to.dhk}")
    private String trainIdCtgToDhk;

    @Value("${train.id.dhk.to.ctg}")
    private String trainIdDhkToCtg;

    @Value("${spring.mail.to}")
    private String emailTo;

//    @Scheduled(cron = "0 1 0 * * Mon")
//    @Scheduled(cron = "0 1 0 * * Wed")
    @Override
    public void checkTicketAvailabilityForDhkToCtg() {
        String date = "05-Dec-2024";//DateUtil.getExpectedDate(days);
        SearchTrainRequest request = new SearchTrainRequest(DHAKA, CHITTAGONG, date, SNIGDHA, trainIdDhkToCtg);
        log.info("request: {}", request);
        Boolean isAvailable = true;//railwayTicketService.getAvailableTickets(request);
        if (isAvailable) {
            sendNotification(String.format("Tickets are available for train %s. %s", trainIdDhkToCtg, isAvailable));
        }
    }

    private void sendNotification(String message) {
        emailService.sendEmail(emailTo, "Notification from RTNS", message);
    }
}
