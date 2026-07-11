package com.example.demo.dto;

import com.example.demo.entity.enums.Priority;
import com.example.demo.entity.enums.TaskStatus;
import java.math.BigDecimal;

public class TaskRequestDto {
    private Long projectId;
    private String title;
    private String description;
    private Priority priority;
    private TaskStatus status;
    private BigDecimal estimatedCost;

    public TaskRequestDto() {}

    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
    public BigDecimal getEstimatedCost() { return estimatedCost; }
    public void setEstimatedCost(BigDecimal estimatedCost) { this.estimatedCost = estimatedCost; }
}
