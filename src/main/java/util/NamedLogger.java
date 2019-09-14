package util;

import java.io.OutputStream;

public class NamedLogger extends Logger {
    private final String name;

    public NamedLogger(String name){
        super();
        this.name = name;
    }

    public NamedLogger(String name, OutputStream infoFile, OutputStream debugFile, OutputStream warnFile){
        super(infoFile, debugFile, warnFile);
        this.name = name;
    }


    @Override
    protected void log(OutputStream out, String message) {
        writeTo(out, String.format("[%s]: %s", name, message));
    }
}
