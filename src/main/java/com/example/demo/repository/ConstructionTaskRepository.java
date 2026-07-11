package com.example.demo.repository;

import com.example.demo.entity.ConstructionTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConstructionTaskRepository extends JpaRepository<ConstructionTask, Long> {
    List<ConstructionTask> findByProjectId(Long projectId);
}
