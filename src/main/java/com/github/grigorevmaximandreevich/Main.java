package com.github.grigorevmaximandreevich;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {

        OrderReaderFactory readerFactory = new OrderReaderFactory();

        CalculationPrice calculationPrice = new CalculationPrice(
                50, BigDecimal.valueOf(10), 5);

        WriterOrdersToFile writerOrdersToFile = new OrderToFileWriter();

        OrderReader txtReader = readerFactory.getReader("src/main/resources/discount_day.txt");
        OrderReader noExtReader = readerFactory.getReader("src/main/resources/discount_day_without_ext");

        writerOrdersToFile.writeToFile(
                calculationPrice.calculatePrice(txtReader.read("src/main/resources/discount_day.txt")),
                "test1.txt"
        );

        writerOrdersToFile.writeToFile(
                calculationPrice.calculatePrice(noExtReader.read("src/main/resources/discount_day_without_ext")),
                "test2.txt"
        );

        System.out.println("Заказы обработаны и сохранены");
    }
}
