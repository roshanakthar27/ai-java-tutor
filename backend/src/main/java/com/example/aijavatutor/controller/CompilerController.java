package com.example.aijavatutor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aijavatutor.model.CodeRequest;
import com.example.aijavatutor.model.CompileResponse;
import com.example.aijavatutor.service.Judge0Service;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/compiler")
public class CompilerController {

    private final Judge0Service judge0Service;

    public CompilerController(Judge0Service judge0Service) {
        this.judge0Service = judge0Service;
    }

    @PostMapping("/run")
    public ResponseEntity<CompileResponse> runCode(
            @Valid @RequestBody CodeRequest request) {

        CompileResponse response = judge0Service.compileAndRun(request);

        return ResponseEntity.ok(response);
    }
}