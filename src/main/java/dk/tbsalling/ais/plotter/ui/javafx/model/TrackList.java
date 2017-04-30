package dk.tbsalling.ais.plotter.ui.javafx.model;

import com.google.common.eventbus.Subscribe;
import dk.tbsalling.ais.tracker.AISTrack;
import dk.tbsalling.ais.tracker.AISTracker;
import dk.tbsalling.ais.tracker.events.AisTrackCreatedEvent;
import dk.tbsalling.ais.tracker.events.AisTrackDeletedEvent;
import dk.tbsalling.ais.tracker.events.AisTrackUpdatedEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
        track.setSource("AIS");
        fillTrack(event.getAisTrack(), track);

        tracks.add(track);
    }

    @Subscribe
    public void handleEvent(AisTrackDeletedEvent event) {
        tracks.removeIf(t -> "AIS".equals(t.getSource()) && event.getMmsi() == t.getMmsi());
    }

    @Subscribe
    public void handleEvent(AisTrackUpdatedEvent event) {
        tracks.filtered(t -> "AIS".equals(t.getSource()) && event.getMmsi() == t.getMmsi()).forEach(
            t -> fillTrack(event.getAisTrack(), t)
        );
    }

    private static void fillTrack(AISTrack source, Track dest) {
        dest.setMmsi(source.getMmsi());
        dest.setShipname(source.getShipName());
        dest.setLastUpdate(LocalDateTime.ofInstant(source.getTimeOfLastUpdate(), ZoneId.systemDefault()));
    }

}
