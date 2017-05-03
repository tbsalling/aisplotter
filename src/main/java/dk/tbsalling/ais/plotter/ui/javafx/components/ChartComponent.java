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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChartComponent extends VBox {

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

        trackList.getTracks().addListener((ListChangeListener<Track>) c -> {
            while(c.next()) {
                List<? extends Track> added = c.getAddedSubList();
                added.forEach(t -> {
                    Platform.runLater(() -> layer.addTrackSymbol(t));
                });

                List<? extends Track> removed = c.getRemoved();
                removed.forEach(t -> {
                    Platform.runLater(() -> layer.removeTrackSymbol(t.getMmsi()));
                });
            }
        });
    }

    private class TrackSymbol extends Polygon {

        private Track track;

        public TrackSymbol(Track track) {
            super(6.0, 0.0,
                    12.0, 20.0,
                    0.0, 20.0);
            this.track = track;
            this.setFill(Color.RED);
            if (track.getCog() != null)
                getTransforms().add(new Rotate(track.getCog(), 0, 0));
        }

        public Track getTrack() {
            return track;
        }
    }

    private class TrackLayer extends MapLayer {

        public void addTrackSymbol(Track track) {
            if (track.getLatitude() != null && track.getLongitude() != null) {
                MapPoint point = new MapPoint(track.getLatitude(), track.getLongitude());
                Node icon = new TrackSymbol(track);
                addPoint(point, icon);
                addContextMenu(icon);
            }
        }

        public void removeTrackSymbol(long mmsi) {
            points.removeIf(pair -> pair.getValue().getTrack().getMmsi() == mmsi);
        }

        private final ObservableList<Pair<MapPoint, TrackSymbol>> points = FXCollections.observableArrayList();

        private void addPoint(MapPoint p, Node icon) {
            points.add(new Pair(p, icon));
            this.getChildren().add(icon);
            this.markDirty();
        }

        private void addContextMenu(Node node) {
            ContextMenu contextMenu = new ContextMenu();
            contextMenu.setOnShowing(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent e) {
                    System.out.println("showing");
                }
            });
            contextMenu.setOnShown(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent e) {
                    System.out.println("shown");
                }
            });

            MenuItem item1 = new MenuItem("Mark confirmed");
            item1.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    System.out.println("Confirmed");
                }
            });
            MenuItem item2 = new MenuItem("Toggle color");
            item2.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    if (node instanceof Shape) {
                        Paint fill = ((Shape) node).getFill();
                        ((Shape) node).setFill(fill == Color.RED ? Color.GREEN : Color.RED);
                    }
                }
            });
            contextMenu.getItems().addAll(item1, item2);

            node.setOnMousePressed(e -> {
                if (e.isPopupTrigger()) {
                    contextMenu.show((Node)e.getSource(), Side.RIGHT, 5, 5);
                    e.consume();
                }
            });
            
            final TextField textField = new TextField("Type Something");
            textField.setContextMenu(contextMenu);
        }

        @Override
        protected void layoutLayer() {
            for (Pair<MapPoint, TrackSymbol> candidate : points) {
                MapPoint point = candidate.getKey();
                Node icon = candidate.getValue();
                Point2D mapPoint = baseMap.getMapPoint(point.getLatitude(), point.getLongitude());
                icon.setVisible(true);
                icon.setTranslateX(mapPoint.getX());
                icon.setTranslateY(mapPoint.getY());
            }
        }
    }

}