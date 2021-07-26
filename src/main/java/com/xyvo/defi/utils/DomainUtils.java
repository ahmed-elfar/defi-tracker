package com.xyvo.defi.utils;

import org.springframework.beans.factory.annotation.Value;

public final class DomainUtils {

    private DomainUtils() {
    }

    @Value("${sequence.allocation.size}")
    private static String tempValue;

    public static final int DEFAULT_ALLOCATION_SIZE = 100;
    public static final int ADDRESS_LENGTH = 42;
    public static final int TOKEN_SYMBOL_LENGTH = 5;
    public static final String NETWORK_SCHEMA = "network";
    public static final String DOMAIN_SCHEMA  = "domain";
    public static final String TRANSACTIONS_SCHEMA = "transactions";
    public static final String POLYGON_SCHEMA = "polygon";
    public static final String BINANCE_SCHEMA = "binance";

}
