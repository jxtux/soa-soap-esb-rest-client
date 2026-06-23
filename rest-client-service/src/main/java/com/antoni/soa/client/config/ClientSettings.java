package com.antoni.soa.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ClientSettings {
    @Value("${soa.client.esb.base-url:http://esb-service:8080}")
    private String esbBaseUrl;

    @Value("${soa.client.security.user-token:esb-token-2026}")
    private String userToken;

    @Value("${soa.client.security.admin-token:esb-admin-token-2026}")
    private String adminToken;

    @Value("${soa.client.security.client-certificate:SOA-CLIENT-CERT-2026}")
    private String clientCertificate;

    @Value("${soa.client.retry.max-attempts:2}")
    private int maxAttempts;

    @Value("${soa.client.retry.backoff-ms:300}")
    private long backoffMs;

    @Value("${soa.client.timeout.seconds:8}")
    private int timeoutSeconds;

    public String getEsbBaseUrl() { return esbBaseUrl; }
    public String getUserToken() { return userToken; }
    public String getAdminToken() { return adminToken; }
    public String getClientCertificate() { return clientCertificate; }
    public int getMaxAttempts() { return maxAttempts; }
    public long getBackoffMs() { return backoffMs; }
    public int getTimeoutSeconds() { return timeoutSeconds; }
}
