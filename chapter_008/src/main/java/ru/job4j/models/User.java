package ru.job4j.models;

public class User {
    private final int id;
    private String name;
    private String login;
    private String email;
    private String password;
    private long createDate;
    private String role;
    private String city;
    private String country;

    public User(final int id, final String name, final String login, final String email, final String password,
                final long date, final String role, final String city, final String country) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.email = email;
        this.createDate = date;
        this.password = password;
        this.role = role;
        this.city = city;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public int getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public long getCreateDate() {
        return createDate;
    }

    public String getRole() {
        return role;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setPassword(final String password) {
        this.password = password;
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
