package dk.tbsalling.ais.plotter;

import com.google.common.eventbus.Subscribe;
import dk.tbsalling.ais.plotter.io.DemoNmeaReader;
import dk.tbsalling.ais.plotter.ui.javafx.SpringBootJavaFxApplication;
import dk.tbsalling.ais.plotter.ui.javafx.components.MainLayout;
import dk.tbsalling.ais.tracker.AISTracker;
import dk.tbsalling.ais.tracker.events.AisTrackCreatedEvent;
import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class PlotterApplication extends SpringBootJavaFxApplication {

	@Value("${app.ui.title:AIS Plotter}")//
	private String windowTitle;

	@Autowired
	private MainLayout mainLayout;

	@Autowired
	private AISTracker tracker;

	@Override
	public void start(Stage primaryStage) throws IOException, InterruptedException {
		notifyPreloader(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_START));

		initUi(primaryStage);
        initTracker();
	}

    private void initUi(Stage primaryStage) {
	    mainLayout.initialize();

        primaryStage.setTitle(windowTitle);
        primaryStage.setScene(new Scene(mainLayout));
        primaryStage.setResizable(true);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private void initTracker() throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResource("demo.nmea").openStream();
        DemoNmeaReader nmeaReader = new DemoNmeaReader(is, 100, TimeUnit.MILLISECONDS);

        tracker.registerSubscriber(this);

        ExecutorService trackingExecutor = Executors.newSingleThreadExecutor();
        trackingExecutor.submit(() -> {
            try {
                tracker.update(new PipedInputStream(nmeaReader.outputStream(), 1));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        nmeaReader.start();
    }

    @Subscribe
    public void handleEvent(AisTrackCreatedEvent event) {
        System.out.println("CREATED: " + event.getAisTrack());
    }

	public static void main(String[] args) {
		launchApp(PlotterApplication.class, args);
	}

}
