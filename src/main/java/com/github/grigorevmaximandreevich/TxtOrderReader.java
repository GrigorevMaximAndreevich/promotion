package com.github.grigorevmaximandreevich;

public class TxtOrderReader extends OrderReaderAdapter {

    public TxtOrderReader() {
        super("|");
    }

    @Override
    public boolean isSupported(String filePath) {
        return filePath != null && filePath.endsWith(".txt");
    }
}
