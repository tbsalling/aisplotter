package dk.tbsalling.ais.plotter.javafx.components;

import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Thomas Darimont
 */
@Component
public class MainLayout extends GridPane {

    private final SinChartComponent sinChartComponent;
    private final StatusBarComponent statusBarComponent;

    @Autowired
    public MainLayout(SinChartComponent sinChartComponent, StatusBarComponent statusBarComponent) {
        this.sinChartComponent = sinChartComponent;
        this.statusBarComponent = statusBarComponent;

        initComponent();
    }

    private void initComponent() {
        add(this.sinChartComponent, 0, 0);
        add(this.statusBarComponent, 0, 1);
    }
}