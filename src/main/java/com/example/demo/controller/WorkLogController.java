package com.example.demo.controller;

import com.example.demo.dto.LogRequestDto;
import com.example.demo.entity.DailyWorkLog;
import com.example.demo.service.LogReportingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class WorkLogController {

    private final LogReportingService logService;

    public WorkLogController(LogReportingService logService) {
        this.logService = logService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_SUPERVISOR')")
    public ResponseEntity<DailyWorkLog> submitLog(@RequestBody LogRequestDto dto,
                                                   Authentication authentication) {
        return ResponseEntity.ok(logService.submitLog(dto, authentication.getName()));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_SUPERVISOR', 'ROLE_CONTRACTOR')")
    public ResponseEntity<List<DailyWorkLog>> getLogs(@RequestParam(required = false) Long projectId) {
        if (projectId != null) {
            return ResponseEntity.ok(logService.getLogsByProject(projectId));
        }
        return ResponseEntity.ok(logService.getAllLogs());
    }
}
