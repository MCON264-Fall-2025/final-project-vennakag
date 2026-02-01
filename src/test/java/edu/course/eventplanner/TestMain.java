package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.service.GuestListManager;
import edu.course.eventplanner.service.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.*;

public class TestMain {

    @BeforeEach
    void setUp() {
        Main.guestListManager = new GuestListManager();
        Main.taskManager = new TaskManager();
        Main.venueSelector = null;
        Main.venue = null;
        Main.guestList = new LinkedList<>();
        Main.venues = null;
        Main.seatingPlanner = null;
        Main.venueSelected = false;
        Main.seatingChart = null;
    }

    @Test
    void testLoadSampleData(){
        Main.loadSampleData(250);
        assertEquals(250, Main.guestList.size());
        assertNotNull(Main.venueSelector);
        assertNotNull(Main.venues);
    }

    @Test
    void testLoadSampleDataWithZeroGuests(){
        Main.loadSampleData(0);
        assertTrue(Main.guestList.isEmpty());
        assertNotNull(Main.venueSelector);
        assertNotNull(Main.venues);
    }

    @Test
    void testRemoveGuest(){
        Main.loadSampleData(100);
        Guest guest = Main.guestList.get(0);
        Main.callRemoveGuest(guest.getName());
        assertEquals(99,  Main.guestList.size());
    }

    @Test
    void testRemoveGuestWithNonExistingGuest(){
        Main.loadSampleData(100);
        String guestName = "NonExistingGuest";
        Main.callRemoveGuest(guestName);
        assertEquals(100,  Main.guestList.size());
    }

    @Test
    void testSelectVenueWhenBudgetMet(){
        Main.loadSampleData(100);
        Main.selectVenue(5000, 100);
        assertNotNull(Main.venueSelector);
        assertTrue(Main.venueSelected);
        assertNotNull(Main.seatingPlanner);
    }

    @Test
    void testSelectVenueWhenBudgetNotMet(){
        Main.loadSampleData(100);
        Main.selectVenue(4000, 100);
        assertNotNull(Main.venueSelector);
        assertFalse(Main.venueSelected);
        assertNull(Main.seatingPlanner);
    }

    @Test
    void testCallGenerateSeatingChart(){
        Main.loadSampleData(100);
        Main.selectVenue(5000, 100);
        Main.callGenerateSeatingChart();
        assertNotNull(Main.seatingChart);
    }

    @Test
    void testCallGenerateSeatingChartWithZeroGuests(){
        Main.callGenerateSeatingChart();
        assertNull(Main.seatingChart);
    }

}
