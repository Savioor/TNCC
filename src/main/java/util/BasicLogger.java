package util;

import java.io.OutputStream;

public class BasicLogger extends Logger {

    @Override
    protected void log(OutputStream out, String message) {
        writeTo(out, message);
    }
}
