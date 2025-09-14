package com.programmerpagi.toko_online.utils;

import java.time.LocalDateTime;

public final class TransactionNumberGenerator {

    private TransactionNumberGenerator(){}


    public static String generate() {

        LocalDateTime localDateTime = LocalDateTime.now();
        String orderNumber = "TRC-" + localDateTime.getSecond() + localDateTime.getMinute()
                + localDateTime.getHour() + localDateTime.getDayOfMonth()
                + localDateTime.getMonthValue() + localDateTime.getYear();


        return orderNumber;
    }
}
