package com.example.demo.service;

import com.example.demo.dto.LogRequestDto;
import com.example.demo.entity.DailyWorkLog;
import com.example.demo.entity.ProjectInquiry;
import com.example.demo.entity.SystemUser;
import com.example.demo.repository.DailyWorkLogRepository;
import com.example.demo.repository.ProjectInquiryRepository;
import com.example.demo.repository.SystemUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogReportingService {

    private final DailyWorkLogRepository logRepository;
    private final ProjectInquiryRepository inquiryRepository;
    private final SystemUserRepository userRepository;

    public LogReportingService(DailyWorkLogRepository logRepository,
                               ProjectInquiryRepository inquiryRepository,
                               SystemUserRepository userRepository) {
        this.logRepository = logRepository;
        this.inquiryRepository = inquiryRepository;
        this.userRepository = userRepository;
    }

    public DailyWorkLog submitLog(LogRequestDto dto, String supervisorUsername) {
        ProjectInquiry project = inquiryRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));
        SystemUser supervisor = userRepository.findByUsername(supervisorUsername)
                .orElseThrow(() -> new RuntimeException("Supervisor not found"));
        DailyWorkLog log = DailyWorkLog.builder()
                .project(project)
                .supervisor(supervisor)
                .logDate(dto.getLogDate())
                .laborCount(dto.getLaborCount())
                .weatherCondition(dto.getWeatherCondition())
                .notes(dto.getNotes())
                .progressIncrement(dto.getProgressIncrement())
                .build();
        return logRepository.save(log);
    }

    public List<DailyWorkLog> getLogsByProject(Long projectId) {
        return logRepository.findByProjectId(projectId);
    }

    public List<DailyWorkLog> getAllLogs() {
        return logRepository.findAll();
    }
}
