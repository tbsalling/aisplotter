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

import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.MapView;
import dk.tbsalling.ais.plotter.ui.javafx.model.TrackList;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChartComponent extends HBox {

    @Autowired
    private TrackList trackList;

    private MapView mapView;

    public ChartComponent() {
    }

    public void initialize() {
        setPrefWidth(800);
        setPrefHeight(600);
        setHgrow(this, Priority.ALWAYS);

        mapView = new MapView();
        setHgrow(mapView, Priority.ALWAYS);

        mapView.setCenter(new Coordinate(55.0, 11.0));
        mapView.setZoom(8);
        getChildren().add(mapView);

        /*
        trackList.getTracks().addListener(new ListChangeListener<Track>() {
            @Override
            public void onChanged(Change<? extends Track> c) {
                if (c.wasAdded()) {
                    List<? extends Track> added = c.getAddedSubList();
                    added.forEach(t -> {
                        mapView.addMarker(Marker.Provided.RED);
                    };
                }
            }
        });   */

        mapView.initialize();
    }

}