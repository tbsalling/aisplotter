package dk.tbsalling.ais.plotter.io;

import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Thomas on 28-04-2017.
 */
public class NmeaDemoSupplier {

    private final ScheduledExecutorService reader = Executors.newScheduledThreadPool(1);

    private final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public NmeaDemoSupplier() {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResource("ais-sample-1.nmea").openStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            reader.scheduleAtFixedRate(() -> {
                br.readLine();
            }, 0, 2, TimeUnit.SECONDS);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public InputStream openStream() {
        return new InputStream() {
            private String currentBuffer;

            @Override
            public int read() throws IOException {
                if (currentBuffer == null)
                    currentBuffer = queue.take();

                if (currentBuffer != null) {
                    ByteArrayInputStream bais = new ByteArrayInputStream(currentBuffer.getBytes());
                    while (bais.available())

            }
        }

    }

}
