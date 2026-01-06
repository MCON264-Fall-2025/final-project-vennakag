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
            } else {
                sortedGroups.get(groupTag).add(g);
            }
        }
        Map<Integer, List<Guest>> seatingPlans = new TreeMap<>();
        int currTable = 1;
        int seatsRemaining = venue.getSeatsPerTable();
        Iterator<Map.Entry<String, Queue<Guest>>> it = sortedGroups.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Queue<Guest>> entry = it.next();
            Queue<Guest> guestList = entry.getValue();
            if (!guestList.isEmpty() && seatsRemaining > 0) {
                if (seatingPlans.containsKey(currTable)) {
                    seatingPlans.get(currTable).add(guestList.poll());
                    seatsRemaining--;
                } else {
                    seatingPlans.put(currTable, new LinkedList<>());
                    seatingPlans.get(currTable).add(guestList.poll());
                    seatsRemaining--;
                }
            }
            if (seatsRemaining == 0) {
                currTable++;
                seatsRemaining = venue.getSeatsPerTable();
            }
            if (guestList.isEmpty()) {
                it.remove(); // correctly removes by key
            }
        }
        return seatingPlans; }
}
