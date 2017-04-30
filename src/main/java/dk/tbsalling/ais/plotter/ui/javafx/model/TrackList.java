package dk.tbsalling.ais.plotter.ui.javafx.model;

import com.google.common.eventbus.Subscribe;
import dk.tbsalling.ais.tracker.AISTracker;
import dk.tbsalling.ais.tracker.events.AisTrackCreatedEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TrackList {

    public ObservableList<Track> getTracks() {
        return tracks;
    }

    private ObservableList<Track> tracks = FXCollections.observableArrayList();

    @Autowired
    private AISTracker tracker;

    @PostConstruct
    private void postConstruct() {
        tracker.registerSubscriber(this);
    }

    @Subscribe
    public void handleEvent(AisTrackCreatedEvent event) {
        Track track = new Track();
        track.setMmsi(event.getMmsi());
        track.setShipname(event.getAisTrack().getShipName());

        tracks.add(track);
    }

}
