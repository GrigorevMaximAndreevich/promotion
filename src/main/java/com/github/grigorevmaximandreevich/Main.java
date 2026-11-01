package com.github.grigorevmaximandreevich;

import java.math.BigDecimal;

public class Main {
    public static void main( String[] args ) {

        OrderReader txtAdapter = new OrderReaderAdapter("|");
        OrderReader withoutExtAdapter = new OrderReaderAdapter("#");

        CalculationPrice calculationPrice = new CalculationPrice(
                50, BigDecimal.valueOf(10), 5);

        WriterOrdersToFile writerOrdersToFile = new OrderToFileWriter();



        writerOrdersToFile.writeToFile(calculationPrice.calculatePrice(
                txtAdapter.read("src/main/resources/discount_day.txt")),
                "testFile1.txt");
        writerOrdersToFile.writeToFile(calculationPrice.calculatePrice(
                        withoutExtAdapter.read("src/main/resources/discount_day_without_ext")),
                "testFile2.txt");

        System.out.println("Заказы обработаны и сохранены");
    }
}
