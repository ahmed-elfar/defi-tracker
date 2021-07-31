package com.xyvo.defi.utils;

import java.sql.Timestamp;

public final class DomainUtils {

    private DomainUtils() {
    }

    public static final int DEFAULT_ALLOCATION_SIZE = 100;

    public static final int USER_NAME_LENGTH = 50;

    public static final int ADDRESS_LENGTH = 42;
    public static final int TOKEN_SYMBOL_LENGTH = 5;
    public static final int TOKEN_NAME_LENGTH = 75;

    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd hh:mm:ss";

    public static final String NETWORK_SCHEMA = "network";
    public static final String DOMAIN_SCHEMA  = "profile";
    public static final String TRANSACTIONS_SCHEMA = "transactions";
    public static final String POLYGON_SCHEMA = "polygon";
    public static final String BINANCE_SCHEMA = "binance";


    public static Timestamp timestampFormatter(Timestamp timestamp) {
        timestamp.setNanos(0);
        return timestamp;
    }

    public static String timestampToString(Timestamp timestamp) {
        timestamp.setNanos(0);
        return timestamp.toString();
    }
}
