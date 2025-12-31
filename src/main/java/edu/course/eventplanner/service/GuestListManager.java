package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.util.Generators;

import java.util.*;

public class GuestListManager {
    private final LinkedList<Guest> guests = new LinkedList<>();
    private final Map<String, Guest> guestByName = new HashMap<>();

    public GuestListManager(List<Guest> guests) {
        for (Guest guest : guests) {
            this.guests.add(guest);}}

  /*  public GuestListManager() {
        int min = 200;
        int max = 800;
        int n = (int)(Math.random() * (max - min + 1)) + min;
        for (Guest guest : Generators.GenerateGuests(n)){
            this.guests.add(guest);
        }
    }
*/
    public GuestListManager() {

    }
    public void addGuest(Guest guest) {
        guests.add(guest);
        guestByName.put(guest.getName(), guest);}
    public boolean removeGuest(String guestName) {
        if(guestByName.containsKey(guestName)){
            guestByName.remove(guestName);
            for(Guest guest : guests){
                if(guestName.equals(guest.getName())){
                    guests.remove(guest);
                    break;}}
            return true;}
        return false; }
    public Guest findGuest(String guestName) {
        for(Guest guest : guests){
            if(guestName.equals(guest.getName())){
                return guest;}}
        return null;}
    public int getGuestCount() { return guests.size(); }
    public List<Guest> getAllGuests() { return guests; }
}
