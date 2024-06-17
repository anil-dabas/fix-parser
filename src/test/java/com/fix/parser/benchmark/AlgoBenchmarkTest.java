package com.fix.parser.benchmark;


import com.fix.parser.service.ParserService;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static com.fix.parser.constants.TestConstant.BENCHMARK_ITERATIONS;

public class AlgoBenchmarkTest {


    @Test
    public void benchmarkFixParser() {
        ParserService fixParser = new ParserService();
        String fixMessageStr = "8=FIX.4.2\u00019=178\u000135=D\u000149=BRKR\u000156=INVMGR\u000134=4\u000152=20030615-19:11:01.000\u000111=ID12345\u000121=1\u000155=MSFT\u000154=1\u000160=20030615-19:11:01.000\u000138=100\u000140=2\u000144=20.00\u000110=220\u0001";
        byte[] fixMessageBytes = fixMessageStr.getBytes(StandardCharsets.US_ASCII);

        long totalNanoTime = 0;

        for (int i = 0; i < BENCHMARK_ITERATIONS; i++) {
            long startTime = System.nanoTime();
            fixParser.parse(fixMessageBytes);
            long endTime = System.nanoTime();
            totalNanoTime += (endTime - startTime);
        }

        double averageTimePerParse = (double) totalNanoTime / BENCHMARK_ITERATIONS;
        System.out.println("Average time taken per parse: " + averageTimePerParse + " ns");
    }
}
