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
package dk.tbsalling.ais.plotter.javafx.components;

import com.google.common.eventbus.Subscribe;
import dk.tbsalling.ais.tracker.AISTrack;
import dk.tbsalling.ais.tracker.AISTracker;
import dk.tbsalling.ais.tracker.events.AisTrackCreatedEvent;
import dk.tbsalling.ais.tracker.events.AisTrackDeletedEvent;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TrackListComponent extends HBox {

    private ObservableList<AISTrack> tracks = FXCollections.observableArrayList();

    @Autowired
    private AISTracker tracker;

    public TrackListComponent() {
        TableView table = new TableView();
        table.setEditable(false);
        table.setItems(tracks);

        TableColumn mmsi = new TableColumn("MMSI");
        mmsi.setCellValueFactory(new PropertyValueFactory<AISTrack, String>("mmsi"));

        TableColumn callsign = new TableColumn("C/S");
        callsign.setCellValueFactory(new PropertyValueFactory<AISTrack, String>("callsign"));

        TableColumn shipname = new TableColumn("Ship name");
        shipname.setCellValueFactory(new PropertyValueFactory<AISTrack, String>("shipName"));

        table.getColumns().addAll(mmsi, callsign, shipname);

        getChildren().add(table);
    }

    @PostConstruct
    private void postConstruct() {
        tracker.registerSubscriber(this);
    }

    @Subscribe
    public void handleEvent(AisTrackCreatedEvent event) {
        Platform.runLater(() -> {
            tracks.add(event.getAisTrack());
        });
    }

    @Subscribe
    public void handleEvent(AisTrackDeletedEvent event) {
        Platform.runLater(() -> {
            tracks.remove(event.getAisTrack());
        });
    }

}