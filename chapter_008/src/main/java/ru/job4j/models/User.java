package ru.job4j.models;

public class User {
    private final int id;
    private String name;
    private String login;
    private String email;
    private String password;
    private final long createDate;
    private String role;

    public User(final int id, final String name, final String login, final String email,
                final String password, final long date, final String role) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.email = email;
        this.createDate = date;
        this.password = password;
        this.role = role;
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

    @Override
    public String toString() {
        return  "id=" + id
                + ", name='" + name + '\''
                + ", login='" + login + '\''
                + ", email='" + email + '\''
                + ", password='" + password + '\''
                + ", createDate=" + createDate + '\''
                + ", role=" + role
                + '}';
    }
}
