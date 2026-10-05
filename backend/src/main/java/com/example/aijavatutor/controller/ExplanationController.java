package com.example.aijavatutor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aijavatutor.model.AiExplanationResponse;
import com.example.aijavatutor.model.ExplanationRequest;
import com.example.aijavatutor.service.GeminiService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/explanation")
public class ExplanationController {

    private final GeminiService geminiService;

    public ExplanationController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping
    public ResponseEntity<AiExplanationResponse> explainCode(
            @Valid @RequestBody ExplanationRequest request) {

        AiExplanationResponse response =
                geminiService.explainCode(request);

        return ResponseEntity.ok(response);
    }
}