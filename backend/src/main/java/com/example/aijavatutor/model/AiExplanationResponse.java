package com.example.aijavatutor.model;

import java.util.ArrayList;
import java.util.List;

public class AiExplanationResponse {

    private String overview;

    private List<LineExplanation> lines;

    private List<String> concepts;

    private List<String> executionFlow;

    private String selectedLineExplanation;

    private String confidenceSummary;

    public AiExplanationResponse() {
        this.lines = new ArrayList<>();
        this.concepts = new ArrayList<>();
        this.executionFlow = new ArrayList<>();
        this.selectedLineExplanation = "";
    }

    public AiExplanationResponse(
            String overview,
            List<LineExplanation> lines,
            List<String> concepts,
            List<String> executionFlow,
            String selectedLineExplanation,
            String confidenceSummary) {

        this.overview = overview;
        this.lines = lines != null ? lines : new ArrayList<>();
        this.concepts = concepts != null ? concepts : new ArrayList<>();
        this.executionFlow = executionFlow != null
                ? executionFlow
                : new ArrayList<>();
        this.selectedLineExplanation = selectedLineExplanation != null
                ? selectedLineExplanation
                : "";
        this.confidenceSummary = confidenceSummary;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public List<LineExplanation> getLines() {
        return lines;
    }

    public void setLines(List<LineExplanation> lines) {
        this.lines = lines != null
                ? lines
                : new ArrayList<>();
    }

    public List<String> getConcepts() {
        return concepts;
    }

    public void setConcepts(List<String> concepts) {
        this.concepts = concepts != null
                ? concepts
                : new ArrayList<>();
    }

    public List<String> getExecutionFlow() {
        return executionFlow;
    }

    public void setExecutionFlow(List<String> executionFlow) {
        this.executionFlow = executionFlow != null
                ? executionFlow
                : new ArrayList<>();
    }

    public String getSelectedLineExplanation() {
        return selectedLineExplanation;
    }

    public void setSelectedLineExplanation(
            String selectedLineExplanation) {

        this.selectedLineExplanation =
                selectedLineExplanation != null
                        ? selectedLineExplanation
                        : "";
    }

    public String getConfidenceSummary() {
        return confidenceSummary;
    }

    public void setConfidenceSummary(String confidenceSummary) {
        this.confidenceSummary = confidenceSummary;
    }
}