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

import dk.tbsalling.ais.plotter.ui.javafx.model.TrackList;
import dk.tbsalling.ais.tracker.AISTrack;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TrackListComponent extends HBox {

    @Autowired
    private TrackList trackList;

    public TrackListComponent() {
    }

    @PostConstruct
    private void init() {
        TableView table = new TableView();
        table.setEditable(false);
        table.setItems(trackList.getTracks());

        TableColumn mmsi = new TableColumn("MMSI");
        mmsi.setCellValueFactory(new PropertyValueFactory<AISTrack, String>("mmsi"));

        TableColumn callsign = new TableColumn("C/S");
        callsign.setCellValueFactory(new PropertyValueFactory<AISTrack, String>("callsign"));

        TableColumn shipname = new TableColumn("Ship name");
        shipname.setCellValueFactory(new PropertyValueFactory<AISTrack, String>("shipname"));

        table.getColumns().addAll(mmsi, callsign, shipname);

        getChildren().add(table);
    }

}