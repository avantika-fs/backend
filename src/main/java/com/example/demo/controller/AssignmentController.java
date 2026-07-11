package com.example.demo.controller;

import com.example.demo.entity.ContractorAssignment;
import com.example.demo.entity.enums.AssignmentStatus;
import com.example.demo.service.AssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_SUPERVISOR')")
    public ResponseEntity<ContractorAssignment> createAssignment(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(assignmentService.createAssignment(body));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_SUPERVISOR', 'ROLE_CONTRACTOR')")
    public ResponseEntity<List<ContractorAssignment>> getAssignments() {
        return ResponseEntity.ok(assignmentService.getAllAssignments());
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPERVISOR', 'ROLE_CONTRACTOR')")
    public ResponseEntity<ContractorAssignment> updateStatus(@PathVariable Long id,
                                                              @RequestBody Map<String, String> body) {
        AssignmentStatus status = AssignmentStatus.valueOf(body.get("status"));
        return ResponseEntity.ok(assignmentService.updateAssignmentStatus(id, status));
    }
}
