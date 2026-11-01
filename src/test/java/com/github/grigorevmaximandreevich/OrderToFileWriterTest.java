package com.github.grigorevmaximandreevich;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderToFileWriterTest {

    private final WriterOrdersToFile writer = new OrderToFileWriter();

    @TempDir
    Path tempDir;

    @Test
    void writeToFile_shouldCreateCorrectFormat() throws IOException {
        Path outputFile = tempDir.resolve("result.txt");
        Map<String, BigDecimal> prices = new TreeMap<>();
        prices.put("Recovery", new BigDecimal("56700.00"));
        prices.put("Power Engineer", new BigDecimal("180000.00"));

        writer.writeToFile(prices, outputFile.toString());

        assertTrue(Files.exists(outputFile));
        String content = Files.readString(outputFile);

        assertTrue(content.contains("Power Engineer - 180000.00"));
        assertTrue(content.contains("Recovery - 56700.00"));
    }
}
