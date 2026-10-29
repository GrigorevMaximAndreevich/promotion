package com.github.grigorevmaximandreevich;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OrderReaderAdapter implements OrderReader {

    private final String delimiter;

    public OrderReaderAdapter(String delimiter) {
        if (delimiter == null || (!delimiter.equals("\\|") && !delimiter.equals("#"))) {
            throw new RuntimeException("Неверный формат ввода разделителя!");
        }
        this.delimiter = delimiter;
    }

    @Override
    public List<Order> read(String filePath) {
        List<Order> orders = new ArrayList<>();
        for (String line : loadLines(filePath)) {
            String[] parts = line.split(delimiter);
            if (parts.length != 3) {
                throw new RuntimeException("Ошибка в заполнении данных файла: " + line);
            }
            LocalDateTime orderTime = LocalDateTime.parse(parts[0]);
            String companyName = parts[1];
            BigDecimal quantity = new BigDecimal(parts[2]);
            orders.add(new Order(orderTime, companyName, quantity));
        }
        return orders.stream()
                .sorted(Comparator.comparing(Order::getOrderTime))
                .collect(Collectors.toList());
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
