package edu.course.eventplanner.service;

import edu.course.eventplanner.service.model.Venue;
import edu.course.eventplanner.service.service.VenueSelector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VenueSelectorTest {

    List<Venue> venues;

    @BeforeEach
    public void setUp(){
        venues = new ArrayList<>();
        venues.add(new Venue("Hall A", 500, 100, 10, 10));
        venues.add(new Venue("Hall B", 1000, 200, 15, 12));
        venues.add(new Venue("Hall C", 1500, 300, 20, 15));
    }

    @Test
    public void testSelectVenueBudget(){
        VenueSelector venueSelector = new VenueSelector(venues);
        Venue venue = venueSelector.selectVenue(600, 100);
        assertEquals("Hall A", venue.getName());
    }

    @Test
    public void testSelectVenueCapacity(){
        VenueSelector venueSelector = new VenueSelector(venues);
        Venue venue = venueSelector.selectVenue(1200, 150);
        assertEquals("Hall B", venue.getName());
    }

    @Test
    public void testSelectVenueBudgetAndCapacity(){
        VenueSelector venueSelector = new VenueSelector(venues);
        Venue venue = venueSelector.selectVenue(2000, 250);
        assertEquals("Hall C", venue.getName());
    }

    @Test
    public void testSelectVenueNoneAvailable(){
        VenueSelector venueSelector = new VenueSelector(venues);
        Venue venue = venueSelector.selectVenue(2000, 400);
        assertNull(venue);
    }

    @Test
    public void testSelectVenueExactMatch() {
        VenueSelector venueSelector = new VenueSelector(venues);
        Venue venue = venueSelector.selectVenue(500, 100);
        assertEquals("Hall A", venue.getName());
    }

    @Test
    public void testSelectVenueEmptyList() {
        VenueSelector venueSelector = new VenueSelector(new ArrayList<>());
        Venue venue = venueSelector.selectVenue(1000, 100);
        assertNull(venue);
    }

    @Test
    public void testSelectVenueBudgetTooLow() {
        VenueSelector venueSelector = new VenueSelector(venues);
        Venue venue = venueSelector.selectVenue(400, 100);
        assertNull(venue);
    }

    @Test
    public void testSelectVenueCapacityTooHigh() {
        VenueSelector venueSelector = new VenueSelector(venues);
        Venue venue = venueSelector.selectVenue(2000, 400);
        assertNull(venue);
    }

    @Test
    public void testSelectsSmallestSuitableVenue() {
        VenueSelector venueSelector = new VenueSelector(venues);
        Venue venue = venueSelector.selectVenue(2000, 100);
        assertEquals("Hall A", venue.getName());
    }
}