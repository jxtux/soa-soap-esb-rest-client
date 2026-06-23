package com.antoni.soa.esb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EsbSettings {
    @Value("${soa.esb.auth.enabled:true}")
    private boolean authEnabled;

    @Value("${soa.esb.auth.user-token:esb-token-2026}")
    private String userToken;

    @Value("${soa.esb.auth.admin-token:esb-admin-token-2026}")
    private String adminToken;

    @Value("${soa.esb.auth.client-certificate:SOA-CLIENT-CERT-2026}")
    private String clientCertificate;

    @Value("${soa.esb.downstream.partidos-url:http://partidos-service:8081/ws}")
    private String partidosUrl;

    @Value("${soa.esb.downstream.selecciones-url:http://selecciones-service:8082/ws}")
    private String seleccionesUrl;

    @Value("${soa.esb.downstream.estadisticas-url:http://estadisticas-service:8083/ws}")
    private String estadisticasUrl;

    @Value("${soa.esb.retry.max-attempts:3}")
    private int maxAttempts;

    @Value("${soa.esb.retry.backoff-ms:500}")
    private long backoffMs;

    @Value("${soa.esb.timeout.seconds:5}")
    private int timeoutSeconds;

    public boolean isAuthEnabled() { return authEnabled; }
    public String getUserToken() { return userToken; }
    public String getAdminToken() { return adminToken; }
    public String getClientCertificate() { return clientCertificate; }
    public String getPartidosUrl() { return partidosUrl; }
    public String getSeleccionesUrl() { return seleccionesUrl; }
    public String getEstadisticasUrl() { return estadisticasUrl; }
    public int getMaxAttempts() { return maxAttempts; }
    public long getBackoffMs() { return backoffMs; }
    public int getTimeoutSeconds() { return timeoutSeconds; }
}
