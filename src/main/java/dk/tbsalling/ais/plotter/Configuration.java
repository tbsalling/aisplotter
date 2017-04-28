package dk.tbsalling.ais.plotter;

import dk.tbsalling.ais.tracker.AISTracker;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public AISTracker aisTracker() {
        return new AISTracker();
    }

}
