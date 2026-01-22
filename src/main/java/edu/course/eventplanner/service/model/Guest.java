package edu.course.eventplanner.service.model;

public class Guest {
    private final String name;
    private final String groupTag;
    public Guest(String name, String groupTag) {
        this.name = name;
        this.groupTag = groupTag;
    }
    public String getName() { return name; }
    public String getGroupTag() { return groupTag; }
}
