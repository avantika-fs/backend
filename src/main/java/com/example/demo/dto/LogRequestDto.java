package com.example.demo.dto;

import java.time.LocalDate;

public class LogRequestDto {
    private Long projectId;
    private LocalDate logDate;
    private Integer laborCount;
    private String weatherCondition;
    private String notes;
    private Integer progressIncrement;

    public LogRequestDto() {}

    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }
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
