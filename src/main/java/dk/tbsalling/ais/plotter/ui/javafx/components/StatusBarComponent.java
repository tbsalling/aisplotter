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

import com.google.common.eventbus.Subscribe;
import dk.tbsalling.ais.tracker.AISTracker;
import dk.tbsalling.ais.tracker.events.AisTrackCreatedEvent;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

@Component
public class StatusBarComponent extends HBox {

    private Label statusText;

    @Autowired
    private AISTracker tracker;

    public StatusBarComponent() {
        statusText = new Label("StatusBar");
        getChildren().add(statusText);
    }

    @PostConstruct
    private void postConstruct() {
        tracker.registerSubscriber(this);
    }

    @Subscribe
    public void handleEvent(AisTrackCreatedEvent event) {
        Platform.runLater(() -> {
            long mmsi = event.getMmsi();
            String shipName = event.getAisTrack().getShipName();
            shipName = StringUtils.isEmpty(shipName) ? "" : " " + shipName;
            int n = tracker.getNumberOfAisTracks();

            statusText.setText("Now tracking MMSI " + mmsi + shipName + ". Tracking total of " + n + " vessels.");
        });
    }
}