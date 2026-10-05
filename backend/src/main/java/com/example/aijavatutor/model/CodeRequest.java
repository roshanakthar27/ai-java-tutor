package com.example.aijavatutor.model;

import jakarta.validation.constraints.NotBlank;

public class CodeRequest {

    @NotBlank(message = "Java code cannot be empty")
    private String code;

    public CodeRequest() {
    }

    public CodeRequest(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}