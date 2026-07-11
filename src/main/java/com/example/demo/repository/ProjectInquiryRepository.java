package com.example.demo.repository;

import com.example.demo.entity.ProjectInquiry;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectInquiryRepository extends JpaRepository<ProjectInquiry, Long> {

    @EntityGraph(attributePaths = {"client", "contractor"})
    List<ProjectInquiry> findAll();

    @EntityGraph(attributePaths = {"client", "contractor"})
    List<ProjectInquiry> findByClientId(Long clientId);

    @EntityGraph(attributePaths = {"client", "contractor"})
    List<ProjectInquiry> findByContractorId(Long contractorId);

    @EntityGraph(attributePaths = {"client", "contractor"})
    List<ProjectInquiry> findByStatus(ProjectInquiry.InquiryStatus status);

    @EntityGraph(attributePaths = {"client", "contractor"})
    Optional<ProjectInquiry> findById(Long id);
}
