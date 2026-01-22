package edu.course.eventplanner.service;

import edu.course.eventplanner.service.model.Guest;
import edu.course.eventplanner.service.model.Venue;
import edu.course.eventplanner.service.service.SeatingPlanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class SeatingPlannerTest {
    private SeatingPlanner planner;
    private Venue venue;

    @BeforeEach
    void setup() {
        venue = new Venue("Test Venue", 1000, 20, 5, 4);
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

    @Test
    void testMultipleGroups_fillTablesEfficiently() {
        List<Guest> guests = List.of(
                new Guest("A1", "GroupA"),
                new Guest("A2", "GroupA"),
                new Guest("B1", "GroupB"),
                new Guest("B2", "GroupB"),
                new Guest("C1", "GroupC")
        );

        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);

        // Verify all guests are seated
        int totalSeated = seating.values().stream().mapToInt(List::size).sum();
        assertEquals(5, totalSeated);

        // Verify no table exceeds capacity
        seating.values().forEach(table ->
                assertTrue(table.size() <= venue.getSeatsPerTable())
        );
    }

    @Test
    void testSingleGuestPerGroup() {
        List<Guest> guests = List.of(
                new Guest("A", "Group1"),
                new Guest("B", "Group2"),
                new Guest("C", "Group3"),
                new Guest("D", "Group4")
        );

        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);


        assertEquals(1, seating.size());
        assertEquals(4, seating.get(1).size());
    }

    @Test
    void testExactlyFillsOneTable() {
        List<Guest> guests = new ArrayList<>();
        for (int i = 0; i < venue.getSeatsPerTable(); i++) {
            guests.add(new Guest("Guest" + i, "SameGroup"));
        }

        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);

        assertEquals(1, seating.size());
        assertEquals(venue.getSeatsPerTable(), seating.get(1).size());
    }

    @Test
    void testExceedsOneTableByOne() {
        List<Guest> guests = new ArrayList<>();
        int numGuests = venue.getSeatsPerTable() + 1;
        for (int i = 0; i < numGuests; i++) {
            guests.add(new Guest("Guest" + i, "SameGroup"));
        }

        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);

        assertEquals(2, seating.size());
        assertEquals(venue.getSeatsPerTable(), seating.get(1).size());
        assertEquals(1, seating.get(2).size());
    }

    @Test
    void testGroupsWithDifferentSizes() {
        List<Guest> guests = List.of(
                new Guest("A1", "Large"),
                new Guest("A2", "Large"),
                new Guest("A3", "Large"),
                new Guest("B1", "Small"),
                new Guest("C1", "Solo")
        );

        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);

        // Verify all guests seated
        int total = seating.values().stream().mapToInt(List::size).sum();
        assertEquals(5, total);

        // Verify table ordering
        assertTrue(seating.keySet().stream().sorted().toList()
                .equals(new ArrayList<>(seating.keySet())));
    }

    @Test
    void testGenerateSeatingPlanReturnsListOfMaps() {
        List<Guest> guests = List.of(
                new Guest("A1", "GroupA"),
                new Guest("A2", "GroupA")
        );

        Map<Integer, List<Guest>> plans = planner.generateSeating(guests);

        assertNotNull(plans);
        assertFalse(plans.isEmpty());
        assertTrue(plans.containsKey(1));
    }
}