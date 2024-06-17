package com.fix.parser.benchmark;

import com.fix.parser.model.FixMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static com.fix.parser.constants.TestConstant.BENCHMARK_ITERATIONS;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class APIBenchmarkTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/fix/parse";
    }

    @Test
    public void benchmarkParseFixMessage() {
        HttpEntity<List<Integer>> requestEntity = getRequestEntity();
        long totalTime = 0;

        for (int i = 0; i < BENCHMARK_ITERATIONS; i++) {
            long startTime = System.nanoTime();

            ResponseEntity<FixMessage> response = restTemplate.exchange(
                    baseUrl,
                    HttpMethod.POST,
                    requestEntity,
                    FixMessage.class
            );

            long endTime = System.nanoTime();
            totalTime += (endTime - startTime);
        }

        double averageTimeMs = totalTime / (BENCHMARK_ITERATIONS * 1_000_000.0);
        System.out.println("Average time per request: " + averageTimeMs + " ms");
    }

    private static HttpEntity<List<Integer>> getRequestEntity() {
        List<Integer> fixMessage = Arrays.asList(
                56, 61, 70, 73, 88, 46, 52, 46, 50, 1, 57, 61, 49, 55, 56, 1, 51, 53, 61, 68, 1, 52, 57, 61, 66, 82, 75, 82, 1, 53, 54, 61, 73, 78, 86, 77, 71, 82, 1, 51, 52, 61, 52, 1, 53, 50, 61, 50, 48, 48, 51, 48, 54, 49, 53, 45, 49, 57, 58, 49, 49, 58, 48, 49, 46, 48, 48, 48, 1, 49, 49, 61, 73, 68, 49, 50, 51, 52, 53, 1, 50, 49, 61, 49, 1, 53, 53, 61, 77, 83, 70, 84, 1, 53, 52, 61, 49, 1, 54, 48, 61, 50, 48, 48, 51, 48, 54, 49, 53, 45, 49, 57, 58, 49, 49, 58, 48, 49, 46, 48, 48, 48, 1, 51, 56, 61, 49, 48, 48, 1, 52, 48, 61, 50, 1, 52, 52, 61, 50, 48, 46, 48, 48, 1, 49, 48, 61, 50, 50, 48, 1
        );
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        return new HttpEntity<>(fixMessage, headers);
    }
}
