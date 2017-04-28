package dk.tbsalling.ais.plotter.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class NmeaDemoSupplier {

    private final InputStream is;

    public NmeaDemoSupplier(InputStream inputStream, int time, TimeUnit unit) {
        this.is = new InputStream() {
            private String currentLine;
            private int currentIndex;
            private final BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

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

            @Override
            public void close() throws IOException {
                inputStream.close();
            }
        };

    }

    public String readLine() throws IOException {
        StringBuilder sb = new StringBuilder(64);

        int c;
        while((c = is.read()) != -1) {
            if ((char) c != '\n')
                sb.append((char) c);
            else
                return sb.toString();
        }

        is.close();
        return null;
    }

}
