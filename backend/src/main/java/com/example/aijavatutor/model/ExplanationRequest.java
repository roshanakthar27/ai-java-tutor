package com.example.aijavatutor.model;

import jakarta.validation.constraints.NotBlank;

public class ExplanationRequest {

    @NotBlank(message = "Java code cannot be empty")
    private String code;

    private Integer selectedLine;

    public ExplanationRequest() {
    }

    public ExplanationRequest(String code, Integer selectedLine) {
        this.code = code;
        this.selectedLine = selectedLine;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSelectedLine() {
        return selectedLine;
    }

    public void setSelectedLine(Integer selectedLine) {
        this.selectedLine = selectedLine;
    }
}