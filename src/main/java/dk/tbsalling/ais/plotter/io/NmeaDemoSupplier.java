package dk.tbsalling.ais.plotter.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class NmeaDemoSupplier {

    private final InputStream is;
    private final int time;
    private final TimeUnit unit;

    public NmeaDemoSupplier(InputStream is, int time, TimeUnit unit) {
        this.is = is;
        this.time = time;
        this.unit = unit;
    }

    public InputStream openStream() throws IOException {
        return new InputStream() {
            private String currentLine;
            private int currentIndex;
            private final BufferedReader br = new BufferedReader(new InputStreamReader(is));

            @Override
            public int read() throws IOException {
                if (currentLine == null || currentIndex >= currentLine.length()) {
                    currentLine = br.readLine();
                    if (currentLine == null)
                        return -1; // EOF
                    currentLine = currentLine.concat("\n");
                    currentIndex = 0;
                    tryToSleep();
                }

                final char c = currentLine.charAt(currentIndex++);
                return c;
            }

            private void tryToSleep() {
                try {
                    unit.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

    }

}
