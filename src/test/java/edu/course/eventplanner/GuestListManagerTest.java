package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.service.GuestListManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestListManagerTest {

    GuestListManager manager;

    @BeforeEach
    public void setUp() {
        manager = new GuestListManager();
    }

    @Test
    public void testAddGuest(){
        Guest guest = new Guest("John Smith", "Family");
        manager.addGuest(guest);

        assertEquals(1, manager.getGuestCount());
        assertEquals("John Smith", manager.getAllGuests().get(0).getName());
    }

    @Test
    public void testRemoveGuest(){
        Guest guest = new Guest("John Smith", "Family");
        manager.addGuest(guest);
        assertEquals(1, manager.getGuestCount());
        manager.removeGuest(guest.getName());
        assertEquals(0, manager.getGuestCount());
    }

    @Test
    public void testGetGuest(){
        Guest guest = new Guest("John Smith", "Family");
        manager.addGuest(guest);
        assertEquals(guest, manager.findGuest("John Smith"));
    }

    @Test
    public void testGetAllGuests(){
        List<Guest> guestList = new ArrayList<>();
        List<Guest> guestsManager;
        Guest guest = new Guest("John Smith", "Family");
        Guest guest2 = new Guest("Bob Jay", "Family");
        Guest guest3 = new Guest("Alice Denier", "Neighbor");
        manager.addGuest(guest);
        guestList.add(guest);
        manager.addGuest(guest2);
        guestList.add(guest2);
        manager.addGuest(guest3);
        guestList.add(guest3);
        guestsManager = manager.getAllGuests();
        assertEquals(guestList, guestsManager);
    }

    @Test
    public void testGetAllGuestsEmpty(){
        manager.addGuest(new Guest("John Smith", "Family"));
        manager.removeGuest("John Smith");
        assertEquals(0,manager.getGuestCount());
        assertTrue(manager.getAllGuests().isEmpty());

    }

    @Test
    public void testFindNonExistingGuest(){
        Guest guest = new Guest("John Smith", "Family");
        manager.addGuest(guest);
        manager.removeGuest("John Smith");
        assertNull(manager.findGuest("John Smith"));
    }

    @Test
    public void testConstructorPopulatesMapAndList() {
        Guest guest1 = new Guest("Alice", "Group A");
        Guest guest2 = new Guest("Bob", "Group B");
        List<Guest> guests = Arrays.asList(guest1, guest2);

        GuestListManager managerWithGuests = new GuestListManager(guests);

        assertEquals(2, managerWithGuests.getGuestCount());
        assertNotNull(managerWithGuests.getGuestByName().get("Alice"));
        assertNotNull(managerWithGuests.getGuestByName().get("Bob"));
        assertTrue(managerWithGuests.getAllGuests().contains(guest1));
        assertTrue(managerWithGuests.getAllGuests().contains(guest2));
    }

    @Test
    public void testAddGuestUpdatesInternalMap() {
        Guest guest = new Guest("Alice", "VIP");
        manager.addGuest(guest);

        assertTrue(manager.getGuestByName().containsKey("Alice"));
        assertSame(guest, manager.getGuestByName().get("Alice"));
    }

    @Test
    public void testRemoveGuestUpdatesInternalMap() {
        Guest guest = new Guest("Alice", "VIP");
        manager.addGuest(guest);

        assertTrue(manager.removeGuest("Alice"));

        // Verify both internal structures are updated
        assertFalse(manager.getGuestByName().containsKey("Alice"));
        assertFalse(manager.getAllGuests().contains(guest));
    }

    @Test
    public void testMapAndListStayInSync() {
        Guest guest1 = new Guest("Alice", "VIP");
        Guest guest2 = new Guest("Bob", "Regular");
        Guest guest3 = new Guest("Charlie", "VIP");

        manager.addGuest(guest1);
        manager.addGuest(guest2);
        manager.addGuest(guest3);

        // Verify sync after additions
        assertEquals(manager.getGuestCount(), manager.getGuestByName().size());

        manager.removeGuest("Bob");

        // Verify sync after removal
        assertEquals(manager.getGuestCount(), manager.getGuestByName().size());
        assertEquals(2, manager.getGuestCount());
    }

    @Test
    public void testFindGuestUsesMapForEfficiency() {
        // Add many guests
        for (int i = 0; i < 100; i++) {
            manager.addGuest(new Guest("Guest" + i, "Regular"));
        }

        // This should be O(1) using the map, not O(n) iteration
        Guest found = manager.findGuest("Guest50");

        assertNotNull(found);
        assertEquals("Guest50", found.getName());
    }
}