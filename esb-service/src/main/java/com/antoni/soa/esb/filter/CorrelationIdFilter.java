package com.antoni.soa.esb.filter;

import com.antoni.soa.esb.service.AuditService;
import com.antoni.soa.esb.util.CorrelationContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(1)
public class CorrelationIdFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(CorrelationIdFilter.class);
    private static final String HEADER = "X-Correlation-Id";
    private final AuditService auditService;

    public CorrelationIdFilter(AuditService auditService) {
        this.auditService = auditService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long start = System.currentTimeMillis();
        String correlationId = request.getHeader(HEADER);
        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
        }

        CorrelationContext.set(correlationId);
        MDC.put("correlationId", correlationId);
        response.setHeader(HEADER, correlationId);

        try {
            log.info("ESB IN {} {}", request.getMethod(), request.getRequestURI());
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - start;
            String role = (String) request.getAttribute("principalRole");
            auditService.register(correlationId, request.getMethod(), request.getRequestURI(), response.getStatus(), duration, role == null ? "ANONYMOUS" : role);
            log.info("ESB OUT {} {} status={} durationMs={}", request.getMethod(), request.getRequestURI(), response.getStatus(), duration);
            MDC.remove("correlationId");
            CorrelationContext.clear();
        }
    }
}
