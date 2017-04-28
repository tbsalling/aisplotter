package dk.tbsalling.ais.plotter.io;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.TimeUnit;

public class DemoNmeaReaderTest {

    DemoNmeaReader sut;

    @Before
    public void before() throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResource("demo.nmea").openStream();
        this.sut = new DemoNmeaReader(is, 3, TimeUnit.SECONDS);
    }

    @Test @Ignore
    public void readLine() throws IOException, InterruptedException {
        sut.start();

        PipedOutputStream outputStream = sut.outputStream();
        PipedInputStream inputStream = new PipedInputStream(outputStream);
        InputStreamReader isr = new InputStreamReader(inputStream);

        char c;
        while ((c = (char) isr.read()) != -1) {
            System.out.print(c);
        }

    }

}