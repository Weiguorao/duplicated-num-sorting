package main.java.se.xcom.bitmapsorting.utils;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class IOUtils {
    public static void closeQuietly(Writer writer) {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }

    public static void closeQuietly(Reader reader) {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }
}
