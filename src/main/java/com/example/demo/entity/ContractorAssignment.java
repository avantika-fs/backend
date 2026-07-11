package com.example.demo.entity;

import com.example.demo.entity.enums.AssignmentStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contractor_assignments")
public class ContractorAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private ConstructionTask task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contractor_id", nullable = false)
    private SystemUser contractor;

    @Column(name = "assigned_date", nullable = false)
    private LocalDate assignedDate;

    @Column(name = "hourly_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal hourlyRate;

    @Column(name = "hours_allocated", nullable = false)
    private Integer hoursAllocated;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssignmentStatus status;

    public ContractorAssignment() {}

    private ContractorAssignment(Builder builder) {
        this.id = builder.id;
        this.task = builder.task;
        this.contractor = builder.contractor;
        this.assignedDate = builder.assignedDate;
        this.hourlyRate = builder.hourlyRate;
        this.hoursAllocated = builder.hoursAllocated;
        this.status = builder.status;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private ConstructionTask task;
        private SystemUser contractor;
        private LocalDate assignedDate;
        private BigDecimal hourlyRate;
        private Integer hoursAllocated;
        private AssignmentStatus status;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder task(ConstructionTask task) { this.task = task; return this; }
        public Builder contractor(SystemUser contractor) { this.contractor = contractor; return this; }
        public Builder assignedDate(LocalDate assignedDate) { this.assignedDate = assignedDate; return this; }
        public Builder hourlyRate(BigDecimal hourlyRate) { this.hourlyRate = hourlyRate; return this; }
        public Builder hoursAllocated(Integer hoursAllocated) { this.hoursAllocated = hoursAllocated; return this; }
        public Builder status(AssignmentStatus status) { this.status = status; return this; }
        public ContractorAssignment build() { return new ContractorAssignment(this); }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ConstructionTask getTask() { return task; }
    public void setTask(ConstructionTask task) { this.task = task; }
    public SystemUser getContractor() { return contractor; }
    public void setContractor(SystemUser contractor) { this.contractor = contractor; }
    public LocalDate getAssignedDate() { return assignedDate; }
    public void setAssignedDate(LocalDate assignedDate) { this.assignedDate = assignedDate; }
    public BigDecimal getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(BigDecimal hourlyRate) { this.hourlyRate = hourlyRate; }
    public Integer getHoursAllocated() { return hoursAllocated; }
    public void setHoursAllocated(Integer hoursAllocated) { this.hoursAllocated = hoursAllocated; }
    public AssignmentStatus getStatus() { return status; }
    public void setStatus(AssignmentStatus status) { this.status = status; }
}
