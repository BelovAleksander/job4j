package ru.job4j.models;

import java.util.Objects;

public class Personality {
    private String name;
    private String login;
    private String email;
    private String password;

    public Personality(String name, String login, String email, String password) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Personality that = (Personality) o;
        return Objects.equals(name, that.name)
                && Objects.equals(login, that.login)
                && Objects.equals(email, that.email)
                && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, login, email, password);
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
