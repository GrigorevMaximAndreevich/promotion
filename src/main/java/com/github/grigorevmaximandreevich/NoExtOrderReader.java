package com.github.grigorevmaximandreevich;

import java.nio.file.Path;
import java.nio.file.Paths;

public class NoExtOrderReader extends OrderReaderAdapter {

    public NoExtOrderReader() {
        super("#");
    }

    @Override
    public boolean isSupported(String filePath) {
        if (filePath == null) {
            return false;
        }
        Path path = Paths.get(filePath);
        String fileName = path.getFileName().toString();
        return !fileName.contains(".");
    }
}
