package dk.tbsalling.ais.plotter;

import dk.tbsalling.ais.plotter.javafx.SpringBootJavaFxApplication;
import dk.tbsalling.ais.plotter.javafx.components.MainLayout;
import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlotterApplication extends SpringBootJavaFxApplication {

	@Value("${app.ui.title:AIS Plotter}")//
	private String windowTitle;

	@Autowired
	private MainLayout mainLayout;

	@Override
	public void start(Stage primaryStage) throws Exception {
		notifyPreloader(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_START));

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
