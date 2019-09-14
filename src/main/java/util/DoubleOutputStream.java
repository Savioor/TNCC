package util;

import java.io.IOException;
import java.io.OutputStream;

public class DoubleOutputStream extends OutputStream {

    private OutputStream out1, out2;

    public OutputStream getOut1() {
        return out1;
    }

    public void setOut1(OutputStream out1) {
        this.out1 = out1;
    }

    public OutputStream getOut2() {
        return out2;
    }

    public void setOut2(OutputStream out2) {
        this.out2 = out2;
    }

    public DoubleOutputStream(OutputStream out1, OutputStream out2){
        this.out1 = out1;
        this.out2 = out2;
    }

    @Override
    public void write(int b) throws IOException {
        out1.write(b);
        out2.write(b);
    }
}
