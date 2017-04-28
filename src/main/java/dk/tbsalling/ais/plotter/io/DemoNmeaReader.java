package dk.tbsalling.ais.plotter.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DemoNmeaReader {

    private final InputStream inputStream;
    private final int time;
    private final TimeUnit timeUnit;
    private final PipedOutputStream outputStream;
    private final ScheduledExecutorService scheduler;

    public DemoNmeaReader(InputStream inputStream, int time, TimeUnit timeUnit) {
        this.inputStream = inputStream;
        this.time = time;
        this.timeUnit = timeUnit;
        this.outputStream = new PipedOutputStream();
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public void start() {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        PrintWriter writer = new PrintWriter(outputStream);

        scheduler.scheduleWithFixedDelay(() -> {
            try {
                String line = br.readLine();
                writer.print(line + '\n');
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, time, timeUnit);
    }

    public PipedOutputStream outputStream() {
        return outputStream;
    }

}
