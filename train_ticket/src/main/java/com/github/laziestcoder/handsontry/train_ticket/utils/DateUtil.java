package com.github.laziestcoder.handsontry.train_ticket.utils;


import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Slf4j
@UtilityClass
public class DateUtil {
    public static final String DATE_FORMAT = "dd-MMM-yyyy";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public static String getExpectedDate(Integer futureDaysNo) {
        if (Objects.isNull(futureDaysNo)) {
            return "";
        }

        LocalDate today = LocalDate.now();
        String formattedDate = today.plusDays(futureDaysNo).format(DATE_FORMATTER);
        log.debug("formattedDate {}", formattedDate);
        return formattedDate;
    }

}
