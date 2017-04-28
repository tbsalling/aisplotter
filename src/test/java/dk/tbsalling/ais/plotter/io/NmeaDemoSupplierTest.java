package dk.tbsalling.ais.plotter.io;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class NmeaDemoSupplierTest {

    NmeaDemoSupplier sut;

    @Before
    public void before() throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResource("demo.nmea").openStream();
        this.sut = new NmeaDemoSupplier(is, 3, TimeUnit.SECONDS);
    }

    @Test @Ignore
    public void openStream() throws Exception {
        InputStream is = sut.openStream();

        int c;
        while((c = is.read()) != -1) {
            char character = (char) c;
            System.out.print(character);
            if (character == '\n')
                System.out.flush();;
        }

    }

}