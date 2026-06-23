package com.antoni.soa.esb.util;

public final class CorrelationContext {
    private static final ThreadLocal<String> CORRELATION_ID = new ThreadLocal<>();

    private CorrelationContext() {}

    public static void set(String correlationId) {
        CORRELATION_ID.set(correlationId);
    }

    public static String get() {
        String value = CORRELATION_ID.get();
        return value == null ? "N/A" : value;
    }

    public static void clear() {
        CORRELATION_ID.remove();
    }
}
