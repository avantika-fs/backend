package com.example.demo.entity;

import com.example.demo.entity.enums.Priority;
import com.example.demo.entity.enums.TaskStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "construction_tasks")
public class ConstructionTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_id", nullable = false)
    private ProjectInquiry project;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Column(name = "estimated_cost", nullable = false, precision = 15, scale = 2)
    private BigDecimal estimatedCost;

    public ConstructionTask() {}

    private ConstructionTask(Builder builder) {
        this.id = builder.id;
        this.project = builder.project;
        this.title = builder.title;
        this.description = builder.description;
        this.priority = builder.priority;
        this.status = builder.status;
        this.estimatedCost = builder.estimatedCost;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private ProjectInquiry project;
        private String title;
        private String description;
        private Priority priority;
        private TaskStatus status;
        private BigDecimal estimatedCost;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder project(ProjectInquiry project) { this.project = project; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder priority(Priority priority) { this.priority = priority; return this; }
        public Builder status(TaskStatus status) { this.status = status; return this; }
        public Builder estimatedCost(BigDecimal estimatedCost) { this.estimatedCost = estimatedCost; return this; }
        public ConstructionTask build() { return new ConstructionTask(this); }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ProjectInquiry getProject() { return project; }
    public void setProject(ProjectInquiry project) { this.project = project; }
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
