package dk.tbsalling.ais.plotter.javafx.components;

import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Thomas Darimont
 */
@Component
public class MainLayout extends GridPane {

    private final HelloWorldComponent helloWorldComponent;
    private final SinChartComponent sinChartComponent;
    private final StatusBarComponent statusBarComponent;

    @Autowired
    public MainLayout(HelloWorldComponent helloWorldComponent, SinChartComponent sinChartComponent, StatusBarComponent statusBarComponent) {
        this.helloWorldComponent = helloWorldComponent;
        this.sinChartComponent = sinChartComponent;
        this.statusBarComponent = statusBarComponent;

        initComponent();
    }

    private void initComponent() {
        add(this.helloWorldComponent, 0, 0);
        add(this.sinChartComponent, 0, 1);
        add(this.statusBarComponent, 0, 2);
    }
}