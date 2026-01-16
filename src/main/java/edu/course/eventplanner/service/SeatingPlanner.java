package edu.course.eventplanner.service;

import edu.course.eventplanner.model.*;
import java.util.*;

public class SeatingPlanner {
    private final Venue venue;
    public SeatingPlanner(Venue venue) { this.venue = venue; }
    public Map<Integer, List<Guest>> generateSeating(List<Guest> guests) {
        Map<String, Queue<Guest>> sortedGroups = new HashMap<>();
        String groupTag;
        for (Guest g : guests) {
            groupTag = g.getGroupTag();
            if (!sortedGroups.containsKey(groupTag)) {
                sortedGroups.put(g.getGroupTag(), new LinkedList<>());
                sortedGroups.get(groupTag).add(g);
            } else {
                sortedGroups.get(groupTag).add(g);
            }
        }
        Map<Integer, List<Guest>> seatingPlans = new TreeMap<>();
        int currTable = 1;
        int seatsRemaining = venue.getSeatsPerTable();
        for (Queue<Guest> guestQueue : sortedGroups.values()) {

            while (!guestQueue.isEmpty()) {

                if (!seatingPlans.containsKey(currTable)) {
                    seatingPlans.put(currTable, new LinkedList<>());
                }

                seatingPlans.get(currTable).add(guestQueue.poll());
                seatsRemaining--;

                if (seatsRemaining == 0) {
                    currTable++;
                    seatsRemaining = venue.getSeatsPerTable();
                }
            }
        }
        return seatingPlans; }
}
