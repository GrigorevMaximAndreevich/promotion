package com.github.grigorevmaximandreevich;

import java.math.BigDecimal;

public class Main {
    public static void main( String[] args ) {

        OrderReader txtAdapter = new OrderReaderAdapter("\\|");
        OrderReader withoutExtAdapter = new OrderReaderAdapter("#");

        CalculationPrice calculationPrice = new CalculationPrice(
                BigDecimal.valueOf(0.5),
                BigDecimal.valueOf(10),
                BigDecimal.valueOf(0.05));

        WriterOrdersToFile writerOrdersToFile = new OrderToFileWriter();

        writerOrdersToFile.writeToFile(calculationPrice.calculatePrice(
                txtAdapter.read("C:/KulaginJavaEducational/Modul_2/discount_day.txt")),
                "testFile1.txt");
        writerOrdersToFile.writeToFile(calculationPrice.calculatePrice(
                        withoutExtAdapter.read("C:/KulaginJavaEducational/Modul_2/discount_day_without_ext")),
                "testFile2.txt");

        System.out.println("Заказы обработаны и сохранены");
    }
}
