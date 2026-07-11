package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InquiryRequestDto {
    private String siteName;
    private String projectType;
    private String location;
    private Integer bhkCount;
    private Integer floorCount;
    private BigDecimal budget;
    private String duration;
    private LocalDate startDate;
    private LocalDate endDate;

    public InquiryRequestDto() {}

    public String getSiteName() { return siteName; }
    public void setSiteName(String siteName) { this.siteName = siteName; }
    public String getProjectType() { return projectType; }
    public void setProjectType(String projectType) { this.projectType = projectType; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public Integer getBhkCount() { return bhkCount; }
    public void setBhkCount(Integer bhkCount) { this.bhkCount = bhkCount; }
    public Integer getFloorCount() { return floorCount; }
    public void setFloorCount(Integer floorCount) { this.floorCount = floorCount; }
    public BigDecimal getBudget() { return budget; }
    public void setBudget(BigDecimal budget) { this.budget = budget; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}
