package com.example.aijavatutor.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.aijavatutor.model.AiExplanationResponse;
import com.example.aijavatutor.model.ExplanationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

@Service
public class GeminiService {

    private static final String MODEL = "gemini-3.8-flash";

    private final Client client;
    private final ObjectMapper objectMapper;

    public GeminiService() {
        this.client = new Client();
        this.objectMapper = new ObjectMapper();
    }

    public AiExplanationResponse explainCode(
            ExplanationRequest request) {

        if (request == null ||
                request.getCode() == null ||
                request.getCode().isBlank()) {

            return createErrorResponse(
                    "No Java code was provided."
            );
        }

        try {

            String prompt = buildPrompt(request);

            GenerateContentResponse response =
                    client.models.generateContent(
                            MODEL,
                            prompt,
                            null
                    );

            String aiResponse = response.text();

            if (aiResponse == null ||
                    aiResponse.isBlank()) {

                return createErrorResponse(
                        "Gemini returned an empty response."
                );
            }

            String cleanJson =
                    cleanJsonResponse(aiResponse);

            return objectMapper.readValue(
                    cleanJson,
                    AiExplanationResponse.class
            );

        } catch (Exception exception) {

            return createErrorResponse(
                    "Unable to communicate with Gemini: "
                            + getSafeMessage(exception)
            );
        }
    }

    private String buildPrompt(
            ExplanationRequest request) {

        StringBuilder prompt = new StringBuilder();

        prompt.append("""
                You are an expert Java programming tutor teaching
                a complete beginner.

                Your goal is to teach the student how to READ,
                UNDERSTAND, and WRITE Java code.

                Analyze the EXACT Java source code provided by
                the student.

                IMPORTANT SOURCE CODE RULES:

                1. Analyze the source code exactly as provided.

                2. Do not rewrite the code before analyzing it.

                3. Do not combine multiple physical lines.

                4. Do not split one physical line into multiple lines.

                5. Do not invent additional source code.

                6. Preserve the original physical line numbers.

                7. The "line" field must contain the actual physical
                   line number in the student's source code.

                8. The "code" field must contain the exact contents
                   of that physical source line.

                9. Blank lines must still affect line numbering.

                BEGINNER TEACHING RULES:

                1. Explain every meaningful source-code line.

                2. Explain every important keyword.

                3. Explain important identifiers.

                4. Explain important methods.

                5. Explain variables and parameters.

                6. Explain important operators.

                7. Explain important Java symbols.

                8. Keep the explanation simple and beginner-friendly.

                9. Never assume the student already knows Java.

                10. Whenever you use a technical Java term,
                    explain it immediately in simple language.

                11. Explain WHAT the line does.

                12. Explain WHY the line is needed.

                13. Explain HOW the line participates in the program.

                14. Explain the relationship between the current line
                    and nearby lines.

                15. Explain the execution order.

                WORD-BY-WORD EXPLANATION:

                For every meaningful line, identify important parts.

                For example:

                public static void main(String[] args) {

                Important parts include:

                public
                static
                void
                main
                String
                []
                args
                ()
                {

                For every important part explain:

                - what it is
                - what it means
                - why it is used
                - how it affects the program

                Do not explain meaningless individual characters.

                Focus on Java-significant symbols such as:

                braces
                parentheses
                square brackets
                semicolons
                assignment operators
                comparison operators
                arithmetic operators
                logical operators
                dots
                quotation marks

                EXECUTION FLOW:

                Explain the execution order in simple steps.

                The student should understand:

                1. Where Java starts.
                2. What happens first.
                3. What happens next.
                4. Why Java executes statements in that order.
                5. Where the program finishes.

                SELECTED LINE:

                If a selected line number is provided:

                1. Explain the entire program first.
                2. Give extra attention to the selected line.
                3. Explain every important part of that line.
                4. Explain what happens when execution reaches it.
                5. Explain why the line is needed.
                6. Explain what could happen if it were removed
                   or changed.
                7. Explain its relationship with surrounding lines.

                ERROR TEACHING:

                If the code contains an error, explain:

                - exact line number
                - exact problem
                - meaning of the error
                - why Java reports it
                - Java concept involved
                - how a beginner can fix it
                - corrected example when useful

                Do not silently change the student's original code.

                JSON OUTPUT:

                Return ONLY valid JSON.

                Do not return Markdown.

                Do not return a JSON code block.

                Do not write anything before or after the JSON.

                Use exactly this structure:

                {
                  "overview": "Simple explanation of the complete program.",
                  "lines": [
                    {
                      "line": 1,
                      "code": "exact physical source line",
                      "explanation": "Simple explanation of what this line does.",
                      "concept": "Main Java concept used.",
                      "why": "Why this line is needed.",
                      "parts": [
                        {
                          "part": "public",
                          "meaning": "Simple meaning of this part.",
                          "why": "Why this part is used here."
                        }
                      ],
                      "relationship": "How this line connects to surrounding lines."
                    }
                  ],
                  "concepts": [
                    "Important Java concept explained simply."
                  ],
                  "executionFlow": [
                    "Step 1: ...",
                    "Step 2: ...",
                    "Step 3: ..."
                  ],
                  "selectedLineExplanation": "Detailed explanation of the selected line.",
                  "confidenceSummary": "What the student should understand after studying the program."
                }

                JSON REQUIREMENTS:

                - Return valid JSON only.
                - Do not use trailing commas.
                - Escape quotation marks correctly.
                - Include every meaningful physical source line.
                - Preserve the original physical line numbers.
                - Preserve the original source line contents.
                - Do not invent source lines.
                - Include structurally meaningful closing braces.
                - Include important parts inside the parts array.
                - Keep overview relatively short.
                - Put detailed explanations inside lines.
                - Put Java concepts inside concepts.
                - Put execution order inside executionFlow.
                - Put selected-line teaching inside
                  selectedLineExplanation.

                """);

        if (request.getSelectedLine() != null) {

            prompt.append("""
                    
                    The student selected physical line number: %d

                    This number refers to the physical line number
                    in the exact source code below.

                    Give extra attention to this line.

                    """.formatted(
                    request.getSelectedLine()
            ));
        }

        prompt.append("""
                
                BEGIN SOURCE CODE

                """);

        prompt.append(request.getCode());

        prompt.append("""
                
                END SOURCE CODE

                Analyze the source code exactly as provided.

                Return ONLY the JSON object.
                """);

        return prompt.toString();
    }

    private String cleanJsonResponse(
            String response) {

        String cleaned = response.trim();

        if (cleaned.startsWith("```json")) {

            cleaned = cleaned
                    .substring(7)
                    .trim();

        } else if (cleaned.startsWith("```")) {

            cleaned = cleaned
                    .substring(3)
                    .trim();
        }

        if (cleaned.endsWith("```")) {

            cleaned = cleaned
                    .substring(
                            0,
                            cleaned.length() - 3
                    )
                    .trim();
        }

        return cleaned;
    }

    private AiExplanationResponse createErrorResponse(
            String message) {

        return new AiExplanationResponse(
                message,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                "",
                "Gemini request failed."
        );
    }

    private String getSafeMessage(
            Exception exception) {

        if (exception.getMessage() == null ||
                exception.getMessage().isBlank()) {

            return exception.getClass()
                    .getSimpleName();
        }

        return exception.getMessage();
    }
}