import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.SeatingPlanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

public class SeatingPlannerTest {
    private SeatingPlanner planner;
    Map<Integer, List<Guest>> seating;

    @BeforeEach
    void setup() {
        Venue venue = new Venue("Test Venue", 1000, 20, 5, 4);
        planner = new SeatingPlanner(venue);
    }

    @Test
    void sameGroup_shouldSitAtSameTable_whenSpaceAllows() {
        List<Guest> guests = List.of(
                new Guest("A1", "A"),
                new Guest("A2", "A"),
                new Guest("A3", "A")
        );

        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);

        List<Guest> table1 = seating.get(1);

        assertEquals(3, table1.size());
        assertTrue(table1.stream().allMatch(g -> g.getGroupTag().equals("A")));
    }
    @Test
    void largeGroup_spansMultipleTables() {
        List<Guest> guests = List.of(
                new Guest("A1", "A"),
                new Guest("A2", "A"),
                new Guest("A3", "A"),
                new Guest("A4", "A"),
                new Guest("A5", "A")
        );

        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);

        assertEquals(4, seating.get(1).size());
        assertEquals(1, seating.get(2).size());

        assertTrue(seating.get(1).stream().allMatch(g -> g.getGroupTag().equals("A")));
        assertTrue(seating.get(2).stream().allMatch(g -> g.getGroupTag().equals("A")));
    }

    @Test
    void seatingPlan_shouldBeOrderedByTableNumber() {
        List<Guest> guests = List.of(
                new Guest("A1", "A"),
                new Guest("A2", "A"),
                new Guest("A3", "A"),
                new Guest("A4", "A"),
                new Guest("A5", "A")
        );

        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);

        List<Integer> tableNumbers = new ArrayList<>(seating.keySet());

        assertEquals(List.of(1, 2), tableNumbers);
        assertInstanceOf(TreeMap.class, seating);
    }

    @Test
    void testSeatingPlanOnEmptyList() {
        List<Guest> guests = List.of();
        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);
        assertTrue(seating.isEmpty());
    }
}
