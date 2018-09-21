package ru.job4j.models;

import java.util.List;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 05.09.18
 * Класс описывает пользователя музыкальной площадки. Предполагается
 * наличие у пользователя вкусовых предпочтений в жанровой музыке и каких то прав на что то.
 */
public class UserMC {
    private int id;
    private String login;
    private String password;
    private Address address;
    private Role role;
    private List<Music> music;

    public UserMC() {

    }

    public UserMC(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public UserMC(String login, String password, Address address, Role role, List<Music> music) {
        this.login = login;
        this.password = password;
        this.address = address;
        this.role = role;
        this.music = music;
    }


    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public List<Music> getMusic() {
        return music;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setMusic(List<Music> music) {
        this.music = music;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Address getAddress() {

        return address;
    }
}
