import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.VenueSelector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class VenueSelectorTest {

    VenueSelector venueSelector;
    @BeforeEach
    public void setup() {
        List<Venue> venues = List.of(
                new Venue("Grand Ballroom", 5000.00, 300, 30, 10),
                new Venue("Riverside Pavilion", 3500.50, 200, 25, 8),
                new Venue("City Conference Hall", 4200.00, 250, 40, 6),
                new Venue("Garden Terrace", 2800.75, 150, 15, 10),
                new Venue("Historic Banquet House", 6000.00, 350, 35, 10)
        );
        venueSelector = new VenueSelector(venues);
    }

    @Test
    public void testSelectVenueWithinBudgetAndCapacity() {
        double budget = 4000;
        int guestCount = 200;
        Venue selectedVenue = venueSelector.selectVenue(budget, guestCount);
        assertEquals("Riverside Pavilion", selectedVenue.getName());
    }

    @Test
    public void testSelectVenueWithEmptyListOfVenues(){
        VenueSelector emptyVenueSelector = new VenueSelector(new ArrayList<>());
        double budget = 4000;
        int guestCount = 200;
        assertNull(emptyVenueSelector.selectVenue(budget, guestCount));
    }

    @Test
    public void testSelectVenueWithExactBudgetAndCapacity(){
        double budget = 4200;
        int guestCount = 250;
        assertEquals("City Conference Hall", venueSelector.selectVenue(budget, guestCount).getName());
    }

    @Test
    public void testSelectVenueWithNoMatchingVenues(){
        double budget = 400;
        int guestCount = 20;
        assertNull(venueSelector.selectVenue(budget, guestCount));
    }

}
