/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dk.tbsalling.ais.plotter.ui.javafx.components;

import dk.tbsalling.ais.plotter.ui.javafx.model.Track;
import dk.tbsalling.ais.plotter.ui.javafx.model.TrackList;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Component
public class TrackListComponent extends HBox {

    @Autowired
    private TrackList trackList;

    public TrackListComponent() {
    }

    public void initialize() {
        setPrefWidth(800);
        setMaxWidth(600);

        TableView table = new TableView();
        table.setEditable(false);
        table.prefWidthProperty().bind(this.widthProperty());
        table.setItems(trackList.getTracks());

        TableColumn mmsi = new TableColumn("MMSI");
        mmsi.setPrefWidth(90);
        mmsi.setMinWidth(90);
        mmsi.setMaxWidth(100);
        mmsi.setCellValueFactory(new PropertyValueFactory<Track, String>("mmsi"));

        TableColumn<Track, String> lastUpdate = new TableColumn<>("Update");
        lastUpdate.setPrefWidth(90);
        lastUpdate.setMinWidth(90);
        lastUpdate.setMaxWidth(100);
        lastUpdate.setCellValueFactory(t -> new SimpleStringProperty(t.getValue().getLastUpdate().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM))));

        TableColumn shipname = new TableColumn("Ship name");
        shipname.setCellValueFactory(new PropertyValueFactory<Track, String>("shipname"));
        shipname.prefWidthProperty().bind(
            table.widthProperty()
            .subtract(mmsi.widthProperty())
            .subtract(lastUpdate.widthProperty())
            .subtract(2)
        );

        table.getColumns().addAll(mmsi, lastUpdate, shipname);

        getChildren().add(table);
    }

}