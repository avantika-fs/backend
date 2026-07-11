package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "project_inquiries")
public class ProjectInquiry {

    public enum InquiryStatus {
        PENDING, ASSIGNED, REJECTED, PLANNING, ACTIVE, COMPLETED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String siteName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private SystemUser client;

    @Column(nullable = false)
    private String projectType;

    @Column(nullable = false)
    private String location;

    private Integer bhkCount;
    private Integer floorCount;

    @Column(precision = 15, scale = 2)
    private BigDecimal budget;

    private String duration;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contractor_id")
    private SystemUser contractor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InquiryStatus status = InquiryStatus.PENDING;

    public ProjectInquiry() {}

    private ProjectInquiry(Builder builder) {
        this.id = builder.id;
        this.siteName = builder.siteName;
        this.client = builder.client;
        this.projectType = builder.projectType;
        this.location = builder.location;
        this.bhkCount = builder.bhkCount;
        this.floorCount = builder.floorCount;
        this.budget = builder.budget;
        this.duration = builder.duration;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.contractor = builder.contractor;
        this.status = builder.status != null ? builder.status : InquiryStatus.PENDING;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String siteName;
        private SystemUser client;
        private String projectType;
        private String location;
        private Integer bhkCount;
        private Integer floorCount;
        private BigDecimal budget;
        private String duration;
        private LocalDate startDate;
        private LocalDate endDate;
        private SystemUser contractor;
        private InquiryStatus status;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder siteName(String siteName) { this.siteName = siteName; return this; }
        public Builder client(SystemUser client) { this.client = client; return this; }
        public Builder projectType(String projectType) { this.projectType = projectType; return this; }
        public Builder location(String location) { this.location = location; return this; }
        public Builder bhkCount(Integer bhkCount) { this.bhkCount = bhkCount; return this; }
        public Builder floorCount(Integer floorCount) { this.floorCount = floorCount; return this; }
        public Builder budget(BigDecimal budget) { this.budget = budget; return this; }
        public Builder duration(String duration) { this.duration = duration; return this; }
        public Builder startDate(LocalDate startDate) { this.startDate = startDate; return this; }
        public Builder endDate(LocalDate endDate) { this.endDate = endDate; return this; }
        public Builder contractor(SystemUser contractor) { this.contractor = contractor; return this; }
        public Builder status(InquiryStatus status) { this.status = status; return this; }
        public ProjectInquiry build() { return new ProjectInquiry(this); }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSiteName() { return siteName; }
    public void setSiteName(String siteName) { this.siteName = siteName; }
    public SystemUser getClient() { return client; }
    public void setClient(SystemUser client) { this.client = client; }
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
    public SystemUser getContractor() { return contractor; }
    public void setContractor(SystemUser contractor) { this.contractor = contractor; }
    public InquiryStatus getStatus() { return status; }
    public void setStatus(InquiryStatus status) { this.status = status; }
}
