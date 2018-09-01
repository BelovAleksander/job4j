package ru.job4j.models;

public class JSONUser {
    private String firstName;
    private String lastName;
    private String sex;
    private String description;

    public JSONUser() {

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSex() {
        return sex;
    }

    public String getDescription() {
        return description;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "JSONUser{"
                + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", sex='" + sex + '\''
                + ", description='" + description + '}';
    }
}
