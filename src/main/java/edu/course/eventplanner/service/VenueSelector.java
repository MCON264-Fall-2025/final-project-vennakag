package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Venue;
import java.util.*;

public class VenueSelector {
    private final List<Venue> venues;
    public VenueSelector(List<Venue> venues) { this.venues = venues; }
    public Venue selectVenue(double budget, int guestCount) {
        Venue selectedVenue = null;
        for (Venue venue : venues) {
            if(venue.getCost() <= budget && venue.getCapacity() >= guestCount) {
                selectedVenue = venue;
            }
            if(selectedVenue != null){
                return selectedVenue;
            }
        }
        return null; }
}
