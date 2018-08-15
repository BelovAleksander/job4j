package ru.job4j.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class User {
    private final int id;
    private String name;
    private String login;
    private String email;
    private final long createDate;

    public User(final int id, final String name, final String login, String email, final long date) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.email = email;
        this.createDate = date;

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

    public void setName(final String name) {
        this.name = name;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        DateFormat formatter = new SimpleDateFormat("dd:MM:yyyy");
        String dateFormatted = formatter.format(createDate);
        return "User{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", login='" + login + '\''
                + ", email='" + email + '\''
                + ", createDate=" + dateFormatted + '}';
    }
}
