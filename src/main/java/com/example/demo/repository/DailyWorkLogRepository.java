package com.example.demo.repository;

import com.example.demo.entity.DailyWorkLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DailyWorkLogRepository extends JpaRepository<DailyWorkLog, Long> {
    List<DailyWorkLog> findByProjectId(Long projectId);
    List<DailyWorkLog> findBySupervisorId(Long supervisorId);
}
