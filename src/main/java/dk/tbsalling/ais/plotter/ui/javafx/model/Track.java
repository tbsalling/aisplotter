package dk.tbsalling.ais.plotter.ui.javafx.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

public class Track {

    private StringProperty sourceProperty = new SimpleStringProperty(this, "source");
    private ObjectProperty<LocalDateTime> lastUpdateProperty = new SimpleObjectProperty<>(this, "lastUpdate");
    private LongProperty mmsiProperty = new SimpleLongProperty(this, "mmsi");
    private StringProperty shipnameProperty = new SimpleStringProperty(this, "shipname");

    public final String getSource() { return sourceProperty.get(); }
    public final void setSource(String source) { sourceProperty.set(source); }
    public StringProperty sourceProperty() { return sourceProperty ;}

    public final LocalDateTime getLastUpdate() { return lastUpdateProperty.get(); }
    public final void setLastUpdate(LocalDateTime lastUpdate) { lastUpdateProperty.set(lastUpdate); }
    public ObjectProperty<LocalDateTime> lastUpdateProperty() { return lastUpdateProperty ;}

    public final long getMmsi() { return mmsiProperty.get(); }
    public final void setMmsi(long mmsi) { mmsiProperty.set(mmsi); }
    public LongProperty mmsiProperty() { return mmsiProperty ;}

    public final String getShipname() { return shipnameProperty.get(); }
    public final void setShipname(String source) { shipnameProperty.set(source); }
    public StringProperty shipnameProperty() { return shipnameProperty ;}

}
