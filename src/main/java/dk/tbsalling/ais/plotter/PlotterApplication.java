package dk.tbsalling.ais.plotter;

import dk.tbsalling.ais.plotter.io.NmeaDemoSupplier;
import dk.tbsalling.ais.plotter.javafx.SpringBootJavaFxApplication;
import dk.tbsalling.ais.plotter.javafx.components.MainLayout;
import dk.tbsalling.ais.tracker.AISTracker;
import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;

@SpringBootApplication
public class PlotterApplication extends SpringBootJavaFxApplication {

	@Value("${app.ui.title:AIS Plotter}")//
	private String windowTitle;

	@Autowired
	private MainLayout mainLayout;

	//@Autowired
	private AISTracker tracker;

	@Override
	public void start(Stage primaryStage) throws Exception {
		notifyPreloader(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_START));

		InputStream nmeaInputStream = new NmeaDemoSupplier().openStream();
		tracker.update(nmeaInputStream);

		primaryStage.setTitle(windowTitle);
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setResizable(true);
		primaryStage.centerOnScreen();
		primaryStage.show();

	}

	public static void main(String[] args) {
		launchApp(PlotterApplication.class, args);
	}

}
