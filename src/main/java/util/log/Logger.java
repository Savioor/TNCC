package util.log;

import java.io.IOException;
import java.io.OutputStream;

public abstract class Logger {
    private final ConditionalOutputStream warnOutputStream, debugOutputStream, infoOutputStream;

    public Logger(){
        this(System.out, System.out, System.err);
    }

    public Logger(OutputStream infoOutputStream, OutputStream debugOutputStream, OutputStream warnOutputStream){
        this(infoOutputStream, debugOutputStream, warnOutputStream, true, false, true);
    }

    public Logger(OutputStream infoOutputStream, OutputStream debugOutputStream, OutputStream warnOutputStream, boolean info, boolean debug, boolean warn) {
        this.infoOutputStream = new ConditionalOutputStream(infoOutputStream, info);
        this.debugOutputStream = new ConditionalOutputStream(debugOutputStream, debug);
        this.warnOutputStream = new ConditionalOutputStream(warnOutputStream, warn);
    }

    public void setWarn(boolean enabled){
        warnOutputStream.setEnabled(enabled);
    }

    public void setDebug(boolean enabled){
        warnOutputStream.setEnabled(enabled);
    }

    public void setInfo(boolean enabled){
        warnOutputStream.setEnabled(enabled);
    }

    protected abstract void log(OutputStream out, String message);

    public final synchronized void info(String message){
        log(infoOutputStream, message);
    }
    public final synchronized void debug(String message){
        log(debugOutputStream, message);
    }
    public final synchronized void warn(String message){
        log(warnOutputStream, message);
    }

    protected static void writeTo(OutputStream out, String message) {
        try {
            out.write((message + "\r\n").getBytes());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
