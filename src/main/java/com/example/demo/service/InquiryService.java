package com.example.demo.service;

import com.example.demo.dto.InquiryRequestDto;
import com.example.demo.entity.ProjectInquiry;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InquiryService {

    @Transactional
    ProjectInquiry submitInquiry(InquiryRequestDto dto, String clientUsername);

    List<ProjectInquiry> getAllInquiries();

    List<ProjectInquiry> getMyInquiries(String clientUsername);

    List<ProjectInquiry> getContractorInquiries(String contractorUsername);

    @Transactional
    ProjectInquiry convertToProject(Long inquiryId, Long contractorId);

    ProjectInquiry updateInquiryStatus(Long id, ProjectInquiry.InquiryStatus status);
}
