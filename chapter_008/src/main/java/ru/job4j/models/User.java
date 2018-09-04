package ru.job4j.models;

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
}
