package com.github.grigorevmaximandreevich;

import java.util.List;

public interface OrderReader {
    List<Order> read(String filePath);
    boolean isSupported(String filePath);
}
