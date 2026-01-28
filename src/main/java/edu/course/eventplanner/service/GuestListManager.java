package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;

import java.util.*;

public class GuestListManager {
    private final LinkedList<Guest> guests = new LinkedList<>();
    private final Map<String, Guest> guestByName = new HashMap<>();

    public GuestListManager(List<Guest> guests) {
        for (Guest guest : guests) {
            this.guests.add(guest);
            this.guestByName.put(guest.getName(), guest); // FIX: Populate the map
        }
    }
    public GuestListManager() {
    }
    public void addGuest(Guest guest) {
        guests.add(guest);
        guestByName.put(guest.getName(), guest);
    }
    public boolean removeGuest(String guestName) {
        if(guestByName.containsKey(guestName)){
            Guest guest = guestByName.remove(guestName);
            guests.remove(guest);
            return true;
        }
        return false;
    }
    public Guest findGuest(String guestName) {
        return guestByName.get(guestName);
    }
    public int getGuestCount() {
        return guests.size();
    }
    public List<Guest> getAllGuests() {
        return guests;
    }
    public Map<String, Guest> getGuestByName() {
        return guestByName;
    }
}