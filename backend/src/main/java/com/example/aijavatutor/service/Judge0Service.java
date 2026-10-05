package com.example.aijavatutor.service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.aijavatutor.config.Judge0Config;
import com.example.aijavatutor.model.CodeRequest;
import com.example.aijavatutor.model.CompileResponse;

@Service
public class Judge0Service {

    private final WebClient webClient;
    private final Judge0Config judge0Config;

    public Judge0Service(
            WebClient.Builder webClientBuilder,
            Judge0Config judge0Config) {

        this.judge0Config = judge0Config;

        this.webClient = webClientBuilder
                .baseUrl(judge0Config.getApiUrl())
                .build();
    }

    public CompileResponse compileAndRun(CodeRequest request) {

        if (request == null ||
                request.getCode() == null ||
                request.getCode().isBlank()) {

            return new CompileResponse(
                    false,
                    "",
                    "Java code cannot be empty.",
                    null
            );
        }

        try {

            Map<String, Object> submission = new HashMap<>();

            submission.put(
                    "source_code",
                    request.getCode()
            );

            submission.put(
                    "language_id",
                    judge0Config.getJavaLanguageId()
            );

            Map<String, Object> createdSubmission =
                    webClient
                            .post()
                            .uri("/submissions/?base64_encoded=false&wait=false")
                            .headers(this::addAuthentication)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(submission)
                            .retrieve()
                            .bodyToMono(Map.class)
                            .block(Duration.ofSeconds(15));

            if (createdSubmission == null) {

                return new CompileResponse(
                        false,
                        "",
                        "Judge0 returned an empty response.",
                        null
                );
            }

            Object tokenObject =
                    createdSubmission.get("token");

            if (tokenObject == null) {

                Object errorObject =
                        createdSubmission.get("error");

                String errorMessage =
                        errorObject == null
                                ? "Judge0 did not return a submission token."
                                : errorObject.toString();

                return new CompileResponse(
                        false,
                        "",
                        errorMessage,
                        null
                );
            }

            String token = tokenObject.toString();

            return getSubmissionResult(token);

        } catch (Exception exception) {

            return new CompileResponse(
                    false,
                    "",
                    "Unable to communicate with Judge0: "
                            + getSafeMessage(exception),
                    null
            );
        }
    }

    private CompileResponse getSubmissionResult(String token) {

        for (int attempt = 0; attempt < 30; attempt++) {

            try {

                Map<String, Object> result =
                        webClient
                                .get()
                                .uri(
                                        "/submissions/{token}?base64_encoded=false",
                                        token
                                )
                                .headers(this::addAuthentication)
                                .retrieve()
                                .bodyToMono(Map.class)
                                .block(Duration.ofSeconds(10));

                if (result == null) {

                    return new CompileResponse(
                            false,
                            "",
                            "Judge0 returned an empty execution result.",
                            null
                    );
                }

                Object statusObject =
                        result.get("status");

                if (statusObject instanceof Map) {

                    Map<?, ?> statusMap =
                            (Map<?, ?>) statusObject;

                    Object statusIdObject =
                            statusMap.get("id");

                    if (statusIdObject != null) {

                        int statusId;

                        try {

                            statusId =
                                    Integer.parseInt(
                                            statusIdObject.toString()
                                    );

                        } catch (NumberFormatException exception) {

                            return new CompileResponse(
                                    false,
                                    "",
                                    "Judge0 returned an invalid status.",
                                    null
                            );
                        }

                        /*
                         * Judge0 status:
                         *
                         * 1 = In Queue
                         * 2 = Processing
                         * 3+ = Finished
                         */

                        if (statusId >= 3) {

                            return createCompileResponse(result);
                        }
                    }
                }

                Thread.sleep(500);

            } catch (InterruptedException exception) {

                Thread.currentThread().interrupt();

                return new CompileResponse(
                        false,
                        "",
                        "Judge0 execution was interrupted.",
                        null
                );

            } catch (Exception exception) {

                return new CompileResponse(
                        false,
                        "",
                        "Unable to retrieve Judge0 result: "
                                + getSafeMessage(exception),
                        null
                );
            }
        }

        return new CompileResponse(
                false,
                "",
                "Judge0 execution timed out.",
                null
        );
    }

    private CompileResponse createCompileResponse(
            Map<String, Object> result) {

        String stdout =
                getStringValue(result, "stdout");

        String stderr =
                getStringValue(result, "stderr");

        String compileOutput =
                getStringValue(result, "compile_output");

        String message =
                getStringValue(result, "message");

        Integer exitCode = null;

        Object exitCodeObject =
                result.get("exit_code");

        if (exitCodeObject != null) {

            try {

                exitCode =
                        Integer.parseInt(
                                exitCodeObject.toString()
                        );

            } catch (NumberFormatException ignored) {

                exitCode = null;
            }
        }

        String error = "";

        if (!compileOutput.isBlank()) {

            error = compileOutput;

        } else if (!stderr.isBlank()) {

            error = stderr;

        } else if (!message.isBlank()) {

            error = message;
        }

        boolean success =
                error.isBlank()
                        && (exitCode == null || exitCode == 0);

        return new CompileResponse(
                success,
                stdout,
                error,
                exitCode
        );
    }

    private String getStringValue(
            Map<String, Object> result,
            String key) {

        Object value =
                result.get(key);

        if (value == null) {
            return "";
        }

        return value.toString();
    }

    private void addAuthentication(
            HttpHeaders headers) {

        String apiKey =
                judge0Config.getApiKey();

        if (apiKey != null &&
                !apiKey.isBlank()) {

            headers.set(
                    "X-Auth-Token",
                    apiKey
            );
        }
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