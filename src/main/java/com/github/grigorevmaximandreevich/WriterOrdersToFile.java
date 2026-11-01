package com.github.grigorevmaximandreevich;

import java.math.BigDecimal;
import java.util.Map;

public interface WriterOrdersToFile {

    void writeToFile(Map<String, BigDecimal> priceOfCompaniesOrders, String fileName);
}
