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

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import dk.tbsalling.ais.plotter.ui.javafx.model.Track;
import dk.tbsalling.ais.plotter.ui.javafx.model.TrackList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChartComponent extends VBox {

    private class TrackLayer extends MapLayer {
        public void addMarker(long mmsi, String shipname, double lat, double lon) {
            MapPoint point = new MapPoint(lat,lon);
            Node icon = new Circle(5, Color.RED);
            addPoint(point, icon);
        }

        private final ObservableList<Pair<MapPoint, Node>> points = FXCollections.observableArrayList();

        public void addPoint(MapPoint p, Node icon) {
            points.add(new Pair(p, icon));
            this.getChildren().add(icon);
            this.markDirty();
        }

        @Override
        protected void layoutLayer() {
            for (Pair<MapPoint, Node> candidate : points) {
                MapPoint point = candidate.getKey();
                Node icon = candidate.getValue();
                Point2D mapPoint = baseMap.getMapPoint(point.getLatitude(), point.getLongitude());
                icon.setVisible(true);
                icon.setTranslateX(mapPoint.getX());
                icon.setTranslateY(mapPoint.getY());
            }
        }
    };

    @Autowired
    private TrackList trackList;

    private MapView mapView;
    private TrackLayer layer;
    private Text mapStatus;

    public ChartComponent() {
    }

    public void initialize() {
        setPrefWidth(800);
        setPrefHeight(600);
        setVgrow(this, Priority.ALWAYS);

        mapView = new MapView();
        setVgrow(mapView, Priority.ALWAYS);

        mapView.setCenter(52.078663, 4.288788);
        mapView.setZoom(7);
        getChildren().add(mapView);

        // ---

        mapStatus = new Text("***");
        getChildren().add(mapStatus);

        // ---

        layer = new TrackLayer();
        mapView.addLayer(layer);
        layer.setVisible(true);

        // ---

        trackList.getTracks().addListener(new ListChangeListener<Track>() {
            @Override
            public void onChanged(Change<? extends Track> c) {
                while(c.next()) {
                    List<? extends Track> added = c.getAddedSubList();
                    added.forEach(t -> {
                        Platform.runLater(() -> layer.addMarker(t.getMmsi(), t.getShipname(), t.getLatitude(), t.getLongitude()));
                    });
                }
            }
        });

    }

}