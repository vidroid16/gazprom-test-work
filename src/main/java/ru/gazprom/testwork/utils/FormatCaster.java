package ru.gazprom.testwork.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface FormatCaster<T> {
    ByteArrayOutputStream cast(List<T> objects) throws IOException;
}
