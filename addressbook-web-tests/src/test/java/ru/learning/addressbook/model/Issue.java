package ru.learning.addressbook.model;

public class Issue {
	
	private int id;
	private String subject;
	private String description;
	private String stateName;
	
	public int getId() {
        return id;
    }
	
	public String getSubject() {
        return subject;
    }
	
	public String getDescription() {
        return description;
    }
	
	public String getStateName() {
        return stateName;
    }
	
	public Issue withId(int id) {
        this.id = id;
        return this;
    }

    public Issue withSubject(String subject) {
        this.subject = subject;
        return this;
    }
	
	public Issue withDescription(String description) {
        this.description = description;
        return this;
    }
	
	public Issue withStateName(String stateName) {
        this.stateName = stateName;
        return this;
    }
}
