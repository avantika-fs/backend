package com.example.demo.controller;

import com.example.demo.dto.InquiryRequestDto;
import com.example.demo.entity.ProjectInquiry;
import com.example.demo.service.InquiryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inquiries")
public class InquiryController {

    private final InquiryService inquiryService;

    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ProjectInquiry> submitInquiry(@RequestBody InquiryRequestDto dto,
                                                         Authentication authentication) {
        return ResponseEntity.ok(inquiryService.submitInquiry(dto, authentication.getName()));
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<ProjectInquiry>> getMyInquiries(Authentication authentication) {
        return ResponseEntity.ok(inquiryService.getMyInquiries(authentication.getName()));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPERVISOR', 'ROLE_CONTRACTOR')")
    public ResponseEntity<List<ProjectInquiry>> getAllInquiries() {
        return inquiryService.getAllInquiries();
    }

    @PostMapping("/{id}/assign")
    @PreAuthorize("hasAuthority('ROLE_SUPERVISOR')")
    public ResponseEntity<ProjectInquiry> assignContractor(@PathVariable Long id,
                                                            @RequestBody Map<String, Long> body) {
        return ResponseEntity.ok(inquiryService.convertToProject(id, body.get("contractorId")));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPERVISOR', 'ROLE_CONTRACTOR')")
    public ResponseEntity<ProjectInquiry> updateStatus(@PathVariable Long id,
                                                        @RequestBody Map<String, String> body) {
        ProjectInquiry.InquiryStatus status = ProjectInquiry.InquiryStatus.valueOf(body.get("status"));
        return ResponseEntity.ok(inquiryService.updateInquiryStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_SUPERVISOR')")
    public ResponseEntity<String> deleteInquiry(@PathVariable Long id) {
        inquiryService.updateInquiryStatus(id, ProjectInquiry.InquiryStatus.REJECTED);
        return ResponseEntity.ok("Inquiry rejected successfully");
    }
}
