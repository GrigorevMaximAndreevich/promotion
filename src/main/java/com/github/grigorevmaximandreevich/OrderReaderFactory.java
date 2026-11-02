package com.github.grigorevmaximandreevich;

import java.util.ArrayList;
import java.util.List;

public class OrderReaderFactory {

    private final List<OrderReader> readers = new ArrayList<>();

    public OrderReaderFactory() {
        readers.add(new TxtOrderReader());
        readers.add(new NoExtOrderReader());
    }

    public OrderReader getReader(String filePath) {
        for (OrderReader reader : readers) {
            if (reader.isSupported(filePath)) {
                return reader;
            }
        }
        throw new IllegalArgumentException("Не найден подходящий ридер для файла: " + filePath);
    }

    public void registerReader(OrderReader reader) {
        readers.add(reader);
    }
}
