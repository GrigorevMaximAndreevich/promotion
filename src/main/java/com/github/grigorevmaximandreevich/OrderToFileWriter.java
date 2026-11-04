package com.github.grigorevmaximandreevich;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public class OrderToFileWriter implements WriterOrdersToFile {

    @Override
    public void writeToFile(Map<String, BigDecimal> priceOfCompaniesOrders, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<String, BigDecimal> entry : priceOfCompaniesOrders.entrySet()) {
                writer.write(entry.getKey() + " - " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Не удалось записать файл " + fileName + ": " + e.getMessage(), e);
        }
    }
}
