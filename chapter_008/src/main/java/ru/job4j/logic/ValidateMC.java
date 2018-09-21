package ru.job4j.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.models.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 21.09.18
 */

public class ValidateMC {
    private static final ValidateMC INSTANCE = new ValidateMC();
    /**
     * DAO layout
     */
    private static final MCStore STORE = MCStore.getInstance();
    /**
     * ADD, UPDATE, DELETE for Roles, Addresses, Music.
     */
    private final HashMap<String, Function<HttpServletRequest, Integer>> entityActions = new HashMap<>();
    /**
     * ADD, UPDATE, DELETE for Users.
     */
    private final HashMap<String, Function<HttpServletRequest, Integer>> userActions = new HashMap<>();
    /**
     * Queries that returns json string.
     */
    private final HashMap<String, Function<HttpServletRequest, String>> queries = new HashMap<>();
    private final static ObjectMapper CONVERTER = new ObjectMapper();

    private ValidateMC() {
        this.entityActions.put("addRole", addRole());
        this.entityActions.put("updateRole", updateRole());
        this.entityActions.put("deleteRole", deleteRole());
        this.entityActions.put("addAddress", addAddress());
        this.entityActions.put("updateAddress", updateAddress());
        this.entityActions.put("deleteAddress", deleteAddress());
        this.entityActions.put("addMusic", addMusic());
        this.entityActions.put("updateMusic", updateMusic());
        this.entityActions.put("deleteMusic", deleteMusic());
        this.userActions.put("addUser", addUser());
        this.userActions.put("updateUser", updateUser());
        this.userActions.put("deleteUser", deleteUser());
        this.queries.put("getUserID", findUserIDByLogin());
        this.queries.put("getRoles", getRoles());
        this.queries.put("getAddresses", getAddresses());
        this.queries.put("getMusic", getMusic());
        this.queries.put("getUsers", getUsers());
        this.queries.put("getUsersByAddress", getUsersByAddress());
        this.queries.put("getUsersByRole", getUsersByRole());
        this.queries.put("getUsersByMusicType", getUsersByMusicType());
        this.queries.put("getLogins", getAllLogins());
    }

    public static ValidateMC getInstance() {
        return INSTANCE;
    }

    public int executeEntityActions(HttpServletRequest req) {
        return this.entityActions.get(req.getParameter("action")).apply(req);
    }

    public int executeUserActions(HttpServletRequest req) {
        return this.userActions.get(req.getParameter("action")).apply(req);
    }

    public String getJson(HttpServletRequest request) {
        return this.queries.get(request.getParameter("action")).apply(request);
    }

    private Function<HttpServletRequest, String> getRoles() {
        return request -> {
            String result = null;
            try {
                result =  CONVERTER.writerWithDefaultPrettyPrinter().writeValueAsString(findAllRoles());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return result;
        };
    }

    private Function<HttpServletRequest, String> getAddresses() {
        return request -> {
            String result = null;
            try {
                result =  CONVERTER.writerWithDefaultPrettyPrinter().writeValueAsString(findAllAddresses());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return result;
        };
    }

    private Function<HttpServletRequest, String> getMusic() {
        return request -> {
            String result = null;
            try {
                result =  CONVERTER.writerWithDefaultPrettyPrinter().writeValueAsString(findAllMusic());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return result;
        };
    }

    private Function<HttpServletRequest, String> getUsers() {
        return request -> {
            String result = null;
            try {
                result = CONVERTER.writerWithDefaultPrettyPrinter().writeValueAsString(findAllUsers());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return result;
        };
    }

    private Function<HttpServletRequest, String> getAllLogins() {
        return request -> {
            String result = null;
            try {
                result = CONVERTER.writerWithDefaultPrettyPrinter().writeValueAsString(STORE.findAllLogins());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return result;
        };
    }

    private Function<HttpServletRequest, String> getUsersByAddress() {
        return request -> {
            String json = null;
            String address = request.getParameter("value");
            int addressID = STORE.findElementId("addresses", address);
            List<UserMC> list = STORE.findUsersByParameters("address_id", addressID);
            try {
                json = CONVERTER.writerWithDefaultPrettyPrinter().writeValueAsString(list);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return json;
        };
    }

    private Function<HttpServletRequest, String> getUsersByRole() {
        return request -> {
            String json = null;
            String role = request.getParameter("value");
            int roleID = STORE.findElementId("roles", role);
            List<UserMC> list = STORE.findUsersByParameters("role_id", roleID);
            try {
                json = CONVERTER.writerWithDefaultPrettyPrinter().writeValueAsString(list);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return json;
        };
    }

    private Function<HttpServletRequest, String> getUsersByMusicType() {
        return request -> {
            String json = null;
            String musicType = request.getParameter("value");
            int musicTypeID = STORE.findElementId("music", musicType);
            List<Integer> usersId = STORE.getUsersIdFromMusicPref(musicTypeID);
            List<UserMC> users = new ArrayList<>();
            try {
                for (int id : usersId) {
                    users.add(STORE.findUserById(id));
                }
                json = CONVERTER.writerWithDefaultPrettyPrinter().writeValueAsString(users);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        };
    }

    private Function<HttpServletRequest, Integer> addRole() {
        return req -> {
            Entity role = new Role(req.getParameter("name"));
            return STORE.addElement("roles", role);

        };
    }

    private Function<HttpServletRequest, Integer> addAddress() {
        return req -> {
            Entity address = new Address(req.getParameter("name"));
            return STORE.addElement("addresses", address);
        };
    }

    private Function<HttpServletRequest, Integer> addMusic() {
        return req -> {
            Entity music = new Music(req.getParameter("name"));
            return STORE.addElement("music", music);
        };
    }

    private Function<HttpServletRequest, Integer> addUser() {
        return req -> {
            UserMC user = null;
            try {
                user = CONVERTER.readValue(req.getParameter("user"), UserMC.class);
                initEntities(user);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return STORE.addUser(user);
        };
    }

    private void initEntities(UserMC user) {
        int addressID = STORE.findElementId("addresses", user.getAddress().getName());
        int roleID = STORE.findElementId("roles", user.getRole().getName());
        user.getAddress().setId(addressID);
        user.getRole().setId(roleID);
        for (Music el : user.getMusic()) {
            int musicID = STORE.findElementId("music", el.getName());
            el.setId(musicID);
        }
    }

    private Function<HttpServletRequest, Integer> updateRole() {
        return request -> {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            Entity role = new Role(id, name);
            STORE.updateElement("roles", role, role.getId());
            return 0;
        };
    }

    private Function<HttpServletRequest, Integer> updateAddress() {
        return request -> {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            Entity address = new Address(id, name);
            STORE.updateElement("addresses", address, address.getId());
            return 0;
        };
    }

    private Function<HttpServletRequest, Integer> updateMusic() {
        return request -> {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            Entity music = new Music(id, name);
            STORE.updateElement("music", music, music.getId());
            return 0;
        };
    }

    private Function<HttpServletRequest, Integer> updateUser() {
        return request -> {
            try {
                UserMC user = CONVERTER.readValue(request.getParameter("user"), UserMC.class);
                initEntities(user);
                STORE.deleteValuesFromMusicPref(user.getId());
                STORE.updateUser(user.getId(), user);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        };
    }

    private Function<HttpServletRequest, Integer> deleteRole() {
        return request -> {
            int id = Integer.parseInt(request.getParameter("id"));
            STORE.deleteElement("roles", id);
            return 0;
        };
    }

    private Function<HttpServletRequest, Integer> deleteAddress() {
        return request -> {
            int id = Integer.parseInt(request.getParameter("id"));
            STORE.deleteElement("addresses", id);
            return 0;
        };
    }

    private Function<HttpServletRequest, Integer> deleteMusic() {
        return request -> {
            int id = Integer.parseInt(request.getParameter("id"));
            STORE.deleteElement("music", id);
            return 0;
        };
    }

    private Function<HttpServletRequest, Integer> deleteUser() {
        return request -> {
            int id = Integer.parseInt(request.getParameter("id"));
            STORE.deleteValuesFromMusicPref(id);
            STORE.deleteElement("users", id);
            return 0;
        };
    }

    private Function<HttpServletRequest, String> findUserIDByLogin() {
        return request -> {
          String login = request.getParameter("login");
          String res = "";
            try {
                int id = STORE.findUserByLogin(login);
                res = CONVERTER.writerWithDefaultPrettyPrinter().writeValueAsString(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        };
    }

    private List<Role> findAllRoles() {
        List<Role> roleList = new ArrayList<>();
        for (String name : STORE.findAllElements("roles")) {
            int id = STORE.findElementId("roles", name);
            Role el = new Role(id, name);
            roleList.add(el);
        }
        return roleList;
    }

    private List<Address> findAllAddresses() {
        List<Address> addressList = new ArrayList<>();
        for (String name : STORE.findAllElements("addresses")) {
            int id = STORE.findElementId("addresses", name);
            Address el = new Address(id, name);
            addressList.add(el);
        }
        return addressList;
    }

    private List<Music> findAllMusic() {
        List<Music> musicList = new ArrayList<>();
        for (String name : STORE.findAllElements("music")) {
            int id = STORE.findElementId("music", name);
            Music el = new Music(id, name);
            musicList.add(el);
        }
        return musicList;
    }

    private List<UserMC> findAllUsers() {
        return STORE.findAllUsers();
    }
}
