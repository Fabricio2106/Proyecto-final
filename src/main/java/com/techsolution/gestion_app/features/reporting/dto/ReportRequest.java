package com.techsolution.gestion_app.features.reporting.dto;

import java.time.LocalDate;

public class ReportRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private String type; // ejemplo: "FINANCIAL", "GENERAL", etc.

    public ReportRequest() {}

    public ReportRequest(LocalDate startDate, LocalDate endDate, String type) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
