package com.github.grigorevmaximandreevich;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public abstract class OrderReaderAdapter implements OrderReader {

    private final String delimiter;

    public OrderReaderAdapter(String delimiter) {
        if (delimiter == null || delimiter.isEmpty()) {
            throw new IllegalArgumentException("Разделитель не может быть null или пустым");
        }
        this.delimiter = Pattern.quote(delimiter);
    }

    @Override
    public List<Order> read(String filePath) {
        List<Order> orders = new ArrayList<>();
        for (String line : loadLines(filePath)) {
            if (line.isBlank()) {
                continue;
            }
            String[] parts = line.split(delimiter);
            if (parts.length != 3) {
                throw new RuntimeException("Ошибка в заполнении данных файла: " + line);
            }
            LocalDateTime orderTime = LocalDateTime.parse(parts[0]);
            String companyName = parts[1];
            int quantity = Integer.parseInt(parts[2]);
            orders.add(new Order(orderTime, companyName, quantity));
        }
        return orders;
    }

    private List<String> loadLines(String filePath) {
        Path path = Paths.get(filePath);
        List<String> lines;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла: " + filePath, e);
        }
        return lines;
    }
}
