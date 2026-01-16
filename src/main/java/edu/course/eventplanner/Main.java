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
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        GuestListManager gm= new GuestListManager();
        TaskManager tm = new TaskManager();
        VenueSelector selector;
        Venue v = null;
        boolean VenueSelected = false;
        List<Guest> guestList = new LinkedList<>();

        System.out.println("Event Planner Mini — see README for instructions.");
        Scanner sc = new Scanner(System.in);
        int num = -1;

        while(num!=0) {
            System.out.println("====== Menu ======");
            System.out.println("1.Load Sample Data");
            System.out.println("2.Add Guest");
            System.out.println("3.Remove Guest");
            System.out.println("4.Select Venue");
            System.out.println("5.Generate Seating Chart");
            System.out.println("6.Add preparation task");
            System.out.println("7.Execute next task");
            System.out.println("8.Undo last task");
            System.out.println("9.Print Event Summary");
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
                        System.out.println("Loading Sample Data...");
                        break;
                    case 2:
                        int numGuests;
                        System.out.println("Enter num of guests to be added:");
                        numGuests = sc.nextInt();
                        System.out.println("Adding Guests...");
                        guestList = Generators.GenerateGuests(numGuests);
                        for(Guest guest : guestList){
                            gm.addGuest(guest);
                            System.out.println("Guest: " + guest.getName() + " was added successfully");
                        }
                        System.out.println("\n\n\n");
                        break;
                    case 3:
                        String name;
                        System.out.println("Enter guest name to be removed:");
                        name = sc.next();
                        System.out.println("Removing Guest...");
                        if(gm.findGuest(name)!=null){
                            gm.removeGuest(name);
                        }else{
                            System.out.println("Guest does not exist");
                        }
                        System.out.println("\n\n\n");
                        break;
                    case 4:
                        List<Venue> venues = Generators.generateVenues();
                        selector = new VenueSelector(venues);
                        double budget;
                        int guestCount;
                        System.out.println("Enter budget: ");
                        budget = sc.nextDouble();
                        System.out.println("Enter guest count: ");
                        guestCount = sc.nextInt();
                        System.out.println("Selecting Venue...");
                        v = selector.selectVenue(budget,guestCount);
                        VenueSelected = true;
                        System.out.println("Venue: " + v.getName() + " was chosen successfully");
                        break;
                    case 5:
                        System.out.println("Generating Seating Chart...");
                        if(VenueSelected && !guestList.isEmpty()) {
                            SeatingPlanner sp = new SeatingPlanner(v);
                            sp.generateSeating(guestList);
                            System.out.println("Guest seating chart generated successfully");
                        }else{
                            System.out.println("Please select venue or add guests as necessary.");
                        }
                        break;
                    case 6:
                        System.out.println("Adding preparation task...");
                        Task preparationTask = new Task("Preparation Task");
                        tm.addTask(preparationTask);
                        break;
                    case 7:
                        System.out.println("Executing next task...");
                        tm.executeNextTask();
                        break;
                    case 8:
                        System.out.println("Undoing last task...");
                        tm.undoLastTask();
                        break;
                    case 9:
                        System.out.println("Printing Event Summary...");
                        System.out.println("--Event Summary--");
                        System.out.println("Venue: " + v.getName());
                        System.out.println("Guest Count" + gm.getGuestCount());

                        break;
                    default:
                        System.out.println("Invalid Input. Please try again :)");
                }
            }
        }
}
