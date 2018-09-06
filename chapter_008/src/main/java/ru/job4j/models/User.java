package ru.job4j.models;

import java.util.Objects;

public class User {
    private final int id;
    private Personality personality;
    private long createDate;
    private String role;
    private String city;
    private String country;

    public User(final int id, final Personality personality,
                final long date, final String role, final String city, final String country) {
        this.id = id;
        this.createDate = date;
        this.personality = personality;
        this.role = role;
        this.city = city;
        this.country = country;
    }

    public Personality getPersonality() {
        return personality;
    }

    public void setPersonality(Personality personality) {
        this.personality = personality;
    }

    public int getId() {
        return this.id;
    }


    public long getCreateDate() {
        return createDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {

        return city;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id
                && createDate == user.createDate
                && Objects.equals(personality, user.personality)
                && Objects.equals(role, user.role)
                && Objects.equals(city, user.city)
                && Objects.equals(country, user.country);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, personality, createDate, role, city, country);
    }
}
