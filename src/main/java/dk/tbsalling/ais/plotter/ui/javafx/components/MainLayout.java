package dk.tbsalling.ais.plotter.ui.javafx.components;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainLayout extends GridPane {

    private final ChartComponent chartComponent;
    private final StatusBarComponent statusBarComponent;
    private final TrackListComponent trackListComponent;

    @Autowired
    public MainLayout(ChartComponent chartComponent, StatusBarComponent statusBarComponent, TrackListComponent trackListComponent) {
        this.chartComponent = chartComponent;
        this.statusBarComponent = statusBarComponent;
        this.trackListComponent = trackListComponent;
    }

    public void initialize() {
        this.chartComponent.initialize();
        this.trackListComponent.initialize();
        this.statusBarComponent.initialize();

        add(this.chartComponent, 0, 0);
        add(this.trackListComponent, 1, 0);
        add(this.statusBarComponent, 0, 1, 2, 1);

        ColumnConstraints cc = new ColumnConstraints();
        cc.setFillWidth(true);
        cc.setHgrow(Priority.ALWAYS);
        this.getColumnConstraints().add(cc);

        RowConstraints rc = new RowConstraints();
        rc.setFillHeight(true);
        rc.setVgrow(Priority.ALWAYS);
        this.getRowConstraints().add(rc);
    }
}