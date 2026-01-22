package edu.course.eventplanner.service.service;

import edu.course.eventplanner.service.model.Venue;
import java.util.*;

public class VenueSelector {
    private final List<Venue> venues;
    public VenueSelector(List<Venue> venues) { this.venues = venues; }
    public Venue selectVenue(double budget, int guestCount) {
        TreeMap<Double, List<Venue>> validOptions = new TreeMap<>();
        Venue selectedVenue = null;
        if (venues.isEmpty()) {
            return null;
        } else {
            for (Venue venue : venues) {
                if (venue.getCost() <= budget && venue.getCapacity() >= guestCount) {
                    if(validOptions.containsKey(venue.getCost())) {
                        validOptions.get(venue.getCost()).add( venue);
                    }else {
                        validOptions.put(venue.getCost(), new ArrayList<>());
                        validOptions.get(venue.getCost()).add(venue);
                    }
                }
            }
        }
        if (validOptions.isEmpty()) {
            return null;
        }
        Map.Entry<Double, List<Venue>> venues = validOptions.firstEntry();
        for(Venue venue : venues.getValue()) {
            if (selectedVenue == null || venue.getCapacity() < selectedVenue.getCapacity()) {
                selectedVenue = venue;
            }
        }
        return selectedVenue;
    }
}
