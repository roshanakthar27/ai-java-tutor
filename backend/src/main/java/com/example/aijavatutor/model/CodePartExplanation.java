package com.example.aijavatutor.model;

public class CodePartExplanation {

    private String part;
    private String meaning;
    private String why;

    public CodePartExplanation() {
    }

    public CodePartExplanation(
            String part,
            String meaning,
            String why) {

        this.part = part;
        this.meaning = meaning;
        this.why = why;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getWhy() {
        return why;
    }

    public void setWhy(String why) {
        this.why = why;
    }
}