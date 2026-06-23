package com.antoni.soa.esb.controller;

import com.antoni.soa.esb.dto.AuditRecordDto;
import com.antoni.soa.esb.dto.GovernancePolicyDto;
import com.antoni.soa.esb.dto.RouteDto;
import com.antoni.soa.esb.dto.ServiceCatalogDto;
import com.antoni.soa.esb.service.AuditService;
import com.antoni.soa.esb.service.GovernanceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/esb/governance")
public class GovernanceController {
    private final GovernanceService governanceService;
    private final AuditService auditService;

    public GovernanceController(GovernanceService governanceService, AuditService auditService) {
        this.governanceService = governanceService;
        this.auditService = auditService;
    }

    @GetMapping("/catalog")
    public ServiceCatalogDto catalog() {
        return governanceService.catalog();
    }

    @GetMapping("/routes")
    public List<RouteDto> routes() {
        return governanceService.routes();
    }

    @GetMapping("/policies")
    public List<GovernancePolicyDto> policies() {
        return governanceService.policies();
    }

    @GetMapping("/audits")
    public List<AuditRecordDto> audits() {
        return auditService.latest();
    }
}
