package com.example.demo.service;

import com.example.demo.dto.InquiryRequestDto;
import com.example.demo.entity.ProjectInquiry;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InquiryService {

    ProjectInquiry submitInquiry(InquiryRequestDto dto, String clientUsername);

    ResponseEntity<List<ProjectInquiry>> getAllInquiries();

    List<ProjectInquiry> getMyInquiries(String clientUsername);

    List<ProjectInquiry> getContractorInquiries(String contractorUsername);

    ProjectInquiry convertToProject(Long inquiryId, Long contractorId);

    ProjectInquiry updateInquiryStatus(Long id, ProjectInquiry.InquiryStatus status);
}
