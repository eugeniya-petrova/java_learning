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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Issue issue = (Issue) o;

        if (id != issue.id) return false;
        if (subject != null ? !subject.equals(issue.subject) : issue.subject != null) return false;
        if (description != null ? !description.equals(issue.description) : issue.description != null) return false;
        return stateName != null ? stateName.equals(issue.stateName) : issue.stateName == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (stateName != null ? stateName.hashCode() : 0);
        return result;
    }
}
