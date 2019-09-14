package util.log;

import java.io.IOException;
import java.io.OutputStream;

public class ConditionalOutputStream extends OutputStream{
    private OutputStream out;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private boolean enabled;

    public ConditionalOutputStream(OutputStream out){
        this(out, true);
    }

    public ConditionalOutputStream(OutputStream out, boolean enabled) {
        this.out = out;
        this.enabled = enabled;
    }

    @Override
    public void write(int b) throws IOException {
        if(enabled){
            out.write(b);
        }
    }
}
