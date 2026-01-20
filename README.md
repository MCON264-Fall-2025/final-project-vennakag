# Event Planner Mini

This project demonstrates practical use of data structures:
linked lists, stacks, queues, maps, trees, sorting, and searching.

## What You Must Do
- Implement all TODO methods
- Write JUnit 5 tests for core logic
- Pass instructor auto-grading tests
- Explain your design choices in this README

See Canvas assignment for full requirements.

Data structures:
1. TaskManager: A queue was used for collection of tasks because each task has to be done in the order of how they come in.
2. TaskManager: A stack was used for the undo last task method because the last task that goes on the completed stack is the first one that has to be taken off.
3. SeatingPlanner: A queue as used for seating guests so that each guest gets seated in according to who comes first.
4. SeatingPlanner:  A binary tree is used for seating the quests in order.
5. GuestManagement: A linked-list is used for the master guest list for easier insertion and removal of guests.
6. GuestManagement: A map is used to store guests for O(1) lookup.
7. VenueSelector: A BST is used so that all valid options are stored in the BST from lowest to highest. As a result, the lowest-price venues are in the first mapping of the BST and I can retrieve it quickly so that I can select the best capacity-related venue amongst the lowest price venues.

Sorting and searching algorithms:
1. Venue Selector: By using a BST, I was able to sort the lowest price venues automatically. After retrieving the list of the lowest price venues, I looped through the list to get the best capacity.
2. Seating Planner: The guests are sorted automatically by placing them in a Treemap

Big-O:
1. Finding a guest: O(1)
2. Selecting a venue: O(n log n)
3. Generating seating: O(n log n)