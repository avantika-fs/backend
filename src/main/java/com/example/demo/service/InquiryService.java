package com.example.demo.service;

import com.example.demo.dto.InquiryRequestDto;
import com.example.demo.entity.ProjectInquiry;
import com.example.demo.entity.SystemUser;
import com.example.demo.repository.ContractorAssignmentRepository;
import com.example.demo.repository.ProjectInquiryRepository;
import com.example.demo.repository.SystemUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InquiryService {

    private final ProjectInquiryRepository inquiryRepository;
    private final ProjectInquiryRepository projectSiteRepository;
    private final SystemUserRepository userRepository;
    private final ContractorAssignmentRepository assignmentRepository;

    public InquiryService(ProjectInquiryRepository inquiryRepository,
                          ProjectInquiryRepository projectSiteRepository,
                          SystemUserRepository userRepository,
                          ContractorAssignmentRepository assignmentRepository) {
        this.inquiryRepository = inquiryRepository;
        this.projectSiteRepository = projectSiteRepository;
        this.userRepository = userRepository;
        this.assignmentRepository = assignmentRepository;
    }

    @Transactional
    public ProjectInquiry submitInquiry(InquiryRequestDto dto, String clientUsername) {
        SystemUser client = userRepository.findByUsername(clientUsername)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        ProjectInquiry inquiry = ProjectInquiry.builder()
                .siteName(dto.getSiteName())
                .projectType(dto.getProjectType())
                .location(dto.getLocation())
                .bhkCount(dto.getBhkCount())
                .floorCount(dto.getFloorCount())
                .budget(dto.getBudget())
                .duration(dto.getDuration())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .client(client)
                .status(ProjectInquiry.InquiryStatus.PENDING)
                .build();
        return inquiryRepository.save(inquiry);
    }

    public ResponseEntity<List<ProjectInquiry>> getAllInquiries() {
        return ResponseEntity.ok(inquiryRepository.findAll());
    }

    public List<ProjectInquiry> getMyInquiries(String clientUsername) {
        SystemUser client = userRepository.findByUsername(clientUsername)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        return inquiryRepository.findByClientId(client.getId());
    }

    public List<ProjectInquiry> getContractorInquiries(String contractorUsername) {
        SystemUser contractor = userRepository.findByUsername(contractorUsername)
                .orElseThrow(() -> new RuntimeException("Contractor not found"));
        return inquiryRepository.findByContractorId(contractor.getId());
    }

    @Transactional
    public ProjectInquiry convertToProject(Long inquiryId, Long contractorId) {
        ProjectInquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));
        if (inquiry.getStatus() != ProjectInquiry.InquiryStatus.PENDING) {
            throw new RuntimeException("This inquiry has already been assigned or processed.");
        }
        SystemUser contractor = userRepository.findById(contractorId)
                .orElseThrow(() -> new RuntimeException("Contractor not found"));
        inquiry.setStatus(ProjectInquiry.InquiryStatus.PLANNING);
        inquiry.setContractor(contractor);
        return inquiryRepository.save(inquiry);
    }

    public ProjectInquiry updateInquiryStatus(Long id, ProjectInquiry.InquiryStatus status) {
        ProjectInquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));
        inquiry.setStatus(status);
        return inquiryRepository.save(inquiry);
    }
}
