package com.antoni.soa.esb.service;

import com.antoni.soa.esb.dto.AuditRecordDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AuditService {
    private final List<AuditRecordDto> records = Collections.synchronizedList(new ArrayList<>());

    public void register(String correlationId, String method, String path, int status, long durationMs, String principalRole) {
        records.add(new AuditRecordDto(Instant.now(), correlationId, method, path, status, durationMs, principalRole));
        if (records.size() > 100) {
            records.remove(0);
        }
    }

    public List<AuditRecordDto> latest() {
        synchronized (records) {
            List<AuditRecordDto> copy = new ArrayList<>(records);
            Collections.reverse(copy);
            return copy;
        }
    }
}
