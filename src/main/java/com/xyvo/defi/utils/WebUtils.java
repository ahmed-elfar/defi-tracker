package com.xyvo.defi.utils;

public final class WebUtils {

    private WebUtils() {
    }

    public static final String PENDING_ALL = "/transaction/pending";
//    public static final String PENDING_ADD = "/transaction/pending";
    public static final String PENDING_BY_NETWORK = "/transaction/pending/network/{networkId}";
}
