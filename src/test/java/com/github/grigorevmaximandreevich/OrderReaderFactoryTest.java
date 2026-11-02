package com.github.grigorevmaximandreevich;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderReaderFactoryTest {

    @Test
    void getReader_shouldReturnTxtReaderForTxtFiles() {
        OrderReaderFactory factory = new OrderReaderFactory();
        OrderReader reader = factory.getReader("data.txt");

        assertNotNull(reader);
        assertInstanceOf(TxtOrderReader.class, reader);
        assertTrue(reader.isSupported("data.txt"));
    }

    @Test
    void getReader_shouldReturnNoExtReaderForFilesWithoutExtension() {
        OrderReaderFactory factory = new OrderReaderFactory();
        OrderReader reader = factory.getReader("data_file");

        assertNotNull(reader);
        assertInstanceOf(NoExtOrderReader.class, reader);
        assertTrue(reader.isSupported("data_file"));
    }

    @Test
    void getReader_shouldThrowExceptionForUnsupportedFormat() {
        OrderReaderFactory factory = new OrderReaderFactory();

        assertThrows(IllegalArgumentException.class, () -> factory.getReader("data.csv"));
    }
}
