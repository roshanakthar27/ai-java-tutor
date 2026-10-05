package com.example.aijavatutor.model;

import java.util.ArrayList;
import java.util.List;

public class LineExplanation {

    private int line;
    private String code;
    private String explanation;
    private String concept;
    private String why;
    private List<CodePartExplanation> parts;
    private String relationship;

    public LineExplanation() {
        this.parts = new ArrayList<>();
    }

    public LineExplanation(
            int line,
            String code,
            String explanation,
            String concept,
            String why,
            List<CodePartExplanation> parts,
            String relationship) {

        this.line = line;
        this.code = code;
        this.explanation = explanation;
        this.concept = concept;
        this.why = why;
        this.parts = parts != null
                ? parts
                : new ArrayList<>();
        this.relationship = relationship;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public String getWhy() {
        return why;
    }

    public void setWhy(String why) {
        this.why = why;
    }

    public List<CodePartExplanation> getParts() {
        return parts;
    }

    public void setParts(List<CodePartExplanation> parts) {
        this.parts = parts != null
                ? parts
                : new ArrayList<>();
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
}