package ru.job4j.models;

import java.util.Objects;

public class JSONUser {
    private String firstName;
    private String lastName;
    private String sex;
    private String description;

    public JSONUser() {

    }

    public JSONUser(final String firstName, final String lastName, final String sex, final String description) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JSONUser jsonUser = (JSONUser) o;
        return Objects.equals(firstName, jsonUser.firstName)
                && Objects.equals(lastName, jsonUser.lastName)
                && Objects.equals(sex, jsonUser.sex)
                && Objects.equals(description, jsonUser.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(firstName, lastName, sex, description);
    }
}
