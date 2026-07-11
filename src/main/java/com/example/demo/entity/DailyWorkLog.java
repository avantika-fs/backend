package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "daily_work_logs")
public class DailyWorkLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_id", nullable = false)
    private ProjectInquiry project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_id", nullable = false)
    private SystemUser supervisor;

    @Column(name = "log_date", nullable = false)
    private LocalDate logDate;

    @Column(name = "labor_count", nullable = false)
    private Integer laborCount;

    @Column(name = "weather_condition", length = 50)
    private String weatherCondition;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "progress_increment", nullable = false)
    private Integer progressIncrement;

    public DailyWorkLog() {}

    private DailyWorkLog(Builder builder) {
        this.id = builder.id;
        this.project = builder.project;
        this.supervisor = builder.supervisor;
        this.logDate = builder.logDate;
        this.laborCount = builder.laborCount;
        this.weatherCondition = builder.weatherCondition;
        this.notes = builder.notes;
        this.progressIncrement = builder.progressIncrement;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private ProjectInquiry project;
        private SystemUser supervisor;
        private LocalDate logDate;
        private Integer laborCount;
        private String weatherCondition;
        private String notes;
        private Integer progressIncrement;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder project(ProjectInquiry project) { this.project = project; return this; }
        public Builder supervisor(SystemUser supervisor) { this.supervisor = supervisor; return this; }
        public Builder logDate(LocalDate logDate) { this.logDate = logDate; return this; }
        public Builder laborCount(Integer laborCount) { this.laborCount = laborCount; return this; }
        public Builder weatherCondition(String weatherCondition) { this.weatherCondition = weatherCondition; return this; }
        public Builder notes(String notes) { this.notes = notes; return this; }
        public Builder progressIncrement(Integer progressIncrement) { this.progressIncrement = progressIncrement; return this; }
        public DailyWorkLog build() { return new DailyWorkLog(this); }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ProjectInquiry getProject() { return project; }
    public void setProject(ProjectInquiry project) { this.project = project; }
    public SystemUser getSupervisor() { return supervisor; }
    public void setSupervisor(SystemUser supervisor) { this.supervisor = supervisor; }
    public LocalDate getLogDate() { return logDate; }
    public void setLogDate(LocalDate logDate) { this.logDate = logDate; }
    public Integer getLaborCount() { return laborCount; }
    public void setLaborCount(Integer laborCount) { this.laborCount = laborCount; }
    public String getWeatherCondition() { return weatherCondition; }
    public void setWeatherCondition(String weatherCondition) { this.weatherCondition = weatherCondition; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public Integer getProgressIncrement() { return progressIncrement; }
    public void setProgressIncrement(Integer progressIncrement) { this.progressIncrement = progressIncrement; }
}
