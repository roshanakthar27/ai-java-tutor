package com.example.aijavatutor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "judge0")
public class Judge0Config {

    private String apiUrl;
    private String apiKey;
    private int javaLanguageId;

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public int getJavaLanguageId() {
        return javaLanguageId;
    }

    public void setJavaLanguageId(int javaLanguageId) {
        this.javaLanguageId = javaLanguageId;
    }
}