package ru.job4j.models;

import java.util.ArrayList;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 05.09.18
 * Класс описывает пользователя музыкальной площадки. Предполагается
 * наличие у пользователя вкусовых предпочтений в жанровой музыке и каких то прав на что то.
 */
public class MUser {
    private final int id;
    private String login;
    private String password;
    private Role role;
    private ArrayList<Music> music;

    public MUser(int id, String login, String password, Role role, ArrayList<Music> music) {
        this.id = id;
        this.login = login;
        this.password = password;
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

    public ArrayList<Music> getMusic() {
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

    public void setMusic(ArrayList<Music> music) {
        this.music = music;
    }
}
