package com.example.aijavatutor.model;

public class CompileResponse {

    private boolean success;
    private String output;
    private String error;
    private Integer exitCode;

    public CompileResponse() {
    }

    public CompileResponse(boolean success, String output, String error, Integer exitCode) {
        this.success = success;
        this.output = output;
        this.error = error;
        this.exitCode = exitCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getExitCode() {
        return exitCode;
    }

    public void setExitCode(Integer exitCode) {
        this.exitCode = exitCode;
    }
}