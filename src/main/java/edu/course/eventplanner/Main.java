package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Task;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.GuestListManager;
import edu.course.eventplanner.service.SeatingPlanner;
import edu.course.eventplanner.service.TaskManager;
import edu.course.eventplanner.service.VenueSelector;
import edu.course.eventplanner.util.Generators;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    // Make fields static and package-private for testing
    static GuestListManager guestListManager;
    static TaskManager taskManager;
    static VenueSelector venueSelector;
    static Venue venue;
    static List<Guest> guestList;
    static List<Venue> venues;
    static SeatingPlanner seatingPlanner;
    static boolean venueSelected = false;

    public static void main(String[] args) {
        // Initialize
        guestListManager = new GuestListManager();
        taskManager = new TaskManager();
        guestList = new LinkedList<>();

        System.out.println("Event Planner Mini — see README for instructions.");
        Scanner sc = new Scanner(System.in);
        int num = -1;

        // Get initial setup
        System.out.println("Enter initial budget:");
        double budget = sc.nextDouble();
        System.out.println("Enter expected number of guests:");
        int expectedGuests = sc.nextInt();

        while(num != 0) {
            System.out.println("====== Menu ======");
            System.out.println("1. Load Sample Data");
            System.out.println("2. Add Guest");
            System.out.println("3. Remove Guest");
            System.out.println("4. Select Venue");
            System.out.println("5. Generate Seating Chart");
            System.out.println("6. Add preparation task");
            System.out.println("7. Execute next task");
            System.out.println("8. Undo last task");
            System.out.println("9. Print Event Summary");
            System.out.println("0. Exit\n");
            System.out.println("Enter your choice:");

            if (sc.hasNextInt()) {
                num = sc.nextInt();
            }

            switch (num) {
                case 0:
                    System.out.println("Exiting...");
                    break;
                case 1:
                    System.out.println("Enter number of guests to load:");
                    int numGuests = sc.nextInt();
                    loadSampleData(numGuests);
                    System.out.println("Sample data loaded successfully!");
                    break;
                case 2:
                    System.out.println("Enter num of guests to be added:");
                    int numToAdd = sc.nextInt();
                    System.out.println("Adding Guests...");
                    List<Guest> newGuests = Generators.GenerateGuests(numToAdd);
                    for(Guest guest : newGuests){
                        guestListManager.addGuest(guest);
                        guestList.add(guest);
                        System.out.println("Guest: " + guest.getName() + " was added successfully");
                    }
                    System.out.println("\n\n\n");
                    break;
                case 3:
                    System.out.println("Enter guest name to be removed:");
                    String name = sc.next();
                    callRemoveGuest(name);
                    break;
                case 4:
                    System.out.println("Enter budget: ");
                    double venueBudget = sc.nextDouble();
                    System.out.println("Enter guest count: ");
                    int guestCount = sc.nextInt();
                    selectVenue(venueBudget, guestCount);
                    break;
                case 5:
                    callGenerateSeatingChart();
                    break;
                case 6:
                    System.out.println("Adding preparation task...");
                    Task preparationTask = new Task("Preparation Task");
                    taskManager.addTask(preparationTask);
                    break;
                case 7:
                    System.out.println("Executing next task...");
                    taskManager.executeNextTask();
                    break;
                case 8:
                    System.out.println("Undoing last task...");
                    taskManager.undoLastTask();
                    break;
                case 9:
                    System.out.println("Printing Event Summary...");
                    System.out.println("--Event Summary--");
                    if (venue != null) {
                        System.out.println("Venue: " + venue.getName());
                    }
                    System.out.println("Guest Count: " + guestListManager.getGuestCount());
                    break;
                default:
                    System.out.println("Invalid Input. Please try again :)");
            }
        }
    }

    // Helper method: Load sample data
    public static void loadSampleData(int numGuests) {
        guestList = Generators.GenerateGuests(numGuests);
        venues = Generators.generateVenues();

        // Add guests to manager
        for (Guest guest : guestList) {
            guestListManager.addGuest(guest);
        }

        // Initialize venue selector with venues
        venueSelector = new VenueSelector(venues);
    }

    // Helper method: Remove guest
    public static void callRemoveGuest(String guestName) {
        System.out.println("Removing Guest...");
        boolean removed = guestListManager.removeGuest(guestName);

        if (removed) {
            // Also remove from guestList
            guestList.removeIf(g -> g.getName().equals(guestName));
            System.out.println("Guest removed successfully");
        } else {
            System.out.println("Guest does not exist");
        }
        System.out.println("\n\n\n");
    }

    // Helper method: Select venue
    public static void selectVenue(double budget, int guestCount) {
        System.out.println("Selecting Venue...");

        if (venueSelector == null) {
            venues = Generators.generateVenues();
            venueSelector = new VenueSelector(venues);
        }

        venue = venueSelector.selectVenue(budget, guestCount);

        if (venue != null) {
            venueSelected = true;
            seatingPlanner = new SeatingPlanner(venue);
            System.out.println("Venue: " + venue.getName() + " was chosen successfully");
        } else {
            System.out.println("No suitable venue found for the given budget and guest count");
        }

    }

    // Helper method: Generate seating chart
    public static void callGenerateSeatingChart() {
        System.out.println("Generating Seating Chart...");

        if (!venueSelected || guestList.isEmpty()) {
            System.out.println("Please select venue or add guests as necessary.");
            return;
        }

        Map<Integer, List<Guest>> seatingChart = seatingPlanner.generateSeating(guestList);
        System.out.println("Guest seating chart generated successfully");

    }
}
