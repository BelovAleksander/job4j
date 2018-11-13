package ru.job4j.callboard.models;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 15.10.2018
 */
public class User {
    private int id;
    private String email;
    private String password;
    private Role role;

    public User() {

    }

    public User(final int id) {
        this.id = id;
    }

    public User(final String email, final String password, final Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setRole(final Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", email='" + email + '\''
                + ", password='" + password + '\''
                + ", role=" + role
                + '}';
    }
}
