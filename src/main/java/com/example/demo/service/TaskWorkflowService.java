package com.example.demo.service;

import com.example.demo.dto.TaskRequestDto;
import com.example.demo.entity.ConstructionTask;
import com.example.demo.entity.ProjectInquiry;
import com.example.demo.entity.enums.TaskStatus;
import com.example.demo.repository.ConstructionTaskRepository;
import com.example.demo.repository.ProjectInquiryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskWorkflowService {

    private final ConstructionTaskRepository taskRepository;
    private final ProjectInquiryRepository inquiryRepository;

    public TaskWorkflowService(ConstructionTaskRepository taskRepository,
                               ProjectInquiryRepository inquiryRepository) {
        this.taskRepository = taskRepository;
        this.inquiryRepository = inquiryRepository;
    }

    public ConstructionTask createTask(TaskRequestDto dto) {
        ProjectInquiry project = inquiryRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));
        ConstructionTask task = ConstructionTask.builder()
                .project(project)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .priority(dto.getPriority())
                .status(dto.getStatus() != null ? dto.getStatus() : TaskStatus.PENDING)
                .estimatedCost(dto.getEstimatedCost())
                .build();
        return taskRepository.save(task);
    }

    public List<ConstructionTask> getTasksByProject(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    public ConstructionTask updateTaskStatus(Long taskId, TaskStatus status) {
        ConstructionTask task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus(status);
        return taskRepository.save(task);
    }
}
