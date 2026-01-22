import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.service.GuestListManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GuestListManagerTest {

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

}
