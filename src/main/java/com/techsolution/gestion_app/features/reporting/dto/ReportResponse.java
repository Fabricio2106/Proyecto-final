package com.techsolution.gestion_app.features.reporting.dto;

public class ReportResponse {
    private String title;
    private String generatedAt;
    private FinancialReport data;

    public ReportResponse() {}

    public ReportResponse(String title, String generatedAt, FinancialReport data) {
        this.title = title;
        this.generatedAt = generatedAt;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(String generatedAt) {
        this.generatedAt = generatedAt;
    }

    public FinancialReport getData() {
        return data;
    }

    public void setData(FinancialReport data) {
        this.data = data;
    }
}
