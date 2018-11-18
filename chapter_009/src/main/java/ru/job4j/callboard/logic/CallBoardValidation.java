package ru.job4j.callboard.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import ru.job4j.callboard.models.Advert;
import ru.job4j.callboard.models.Model;
import ru.job4j.callboard.models.Photo;
import ru.job4j.callboard.models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author Alexander Belov (whiterabbit.nsk@gamil.com)
 * @since 17.10.18
 */
public class CallBoardValidation {
    private static final ConcurrentHashMap<String, Function<String, String>> ACTIONS = new ConcurrentHashMap<>();
    private static final CallBoardValidation INSTANCE = new CallBoardValidation();
    private static final CallBoardDAO DAO = CallBoardDAO.getInstance();
    private static final ObjectMapper CONVERTER = new ObjectMapper();
    private static final Logger LOG = Logger.getLogger("APP2");

    private CallBoardValidation() {
        ACTIONS.put("addAdvert", addOrUpdateAdvert());
        ACTIONS.put("updateAdvert", addOrUpdateAdvert());
        ACTIONS.put("deleteAdvert", deleteAdvert());
        ACTIONS.put("getAdvert", getAdvertById());
        ACTIONS.put("getAdverts", getAllAdverts());
        ACTIONS.put("changeStatus", changeStatus());
        ACTIONS.put("getPhoto", getPhoto());
        ACTIONS.put("addUser", addUser());
        ACTIONS.put("getUser", getUser());
        ACTIONS.put("getPassword", getPassword());
        ACTIONS.put("getBrands", getBrands());
        ACTIONS.put("getModels", getModels());
        ACTIONS.put("getColors", getColors());
    }

    public static CallBoardValidation getInstance() {
        return INSTANCE;
    }

    public String execute(final HashMap<String, String> param) {
        LOG.info("***validation | execute***");
        LOG.info("action: " + param.get("action"));
        LOG.info("value: " + param.get("value"));
        return ACTIONS.get(param.get("action")).apply(param.get("value"));
    }

    private String convertToJson(final Object obj) {
        String res = null;
        try {
            res = CONVERTER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
    }

    private Function<String, String> addOrUpdateAdvert() {
        return json -> {
            LOG.info("***validation | addAdvert***");
            try {
                final Advert advert = CONVERTER.readValue(json, Advert.class);
                DAO.addOrUpdate(advert);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        };
    }

    private Function<String, String> deleteAdvert() {
        return vin -> {
            LOG.info("***validation | deleteAdvert***");
            DAO.deleteAdvert(Integer.parseInt(vin));
            return null;
        };
    }

    private Function<String, String> getAdvertById() {
        return id -> {
            LOG.info("***validation | getAdvert***");
            return convertToJson((DAO.getObjectById(Advert.class, Integer.parseInt(id))));
        };
    }

    private Function<String, String> getAllAdverts() {
        return str -> {
            LOG.info("***validation | getAllAdverts***");
            return convertToJson(DAO.getAll("from Advert"));
        };
    }

    private Function<String, String> changeStatus() {
        return id -> {
            LOG.info("***validation | changeStatus***");
            final Advert advert = (Advert) DAO.getObjectById(Advert.class, Integer.parseInt(id));
            advert.changeStatus();
            DAO.addOrUpdate(advert);
            return null;
        };
    }

    private Function<String, String> getPhoto() {
        return id -> {
            LOG.info("***validation | getPhoto***");
            final Advert advert = (Advert) DAO.getObjectById(Advert.class, Integer.parseInt(id));
            return advert.getPhoto();
        };
    }

    private Function<String, String> addUser() {
        return json -> {
            LOG.info("***validation | addUser***");
            String id = null;
            try {
                final User user = CONVERTER.readValue(json, User.class);
                DAO.addOrUpdate(user);
                id = user.getId() + "";
            } catch (IOException e) {
                e.printStackTrace();
            }
            return id;
        };
    }

    private Function<String, String> getUser() {
        return email -> {
            LOG.info("***validation | getUser***");
            final List<User> users = DAO.getAll("from User");
            User result = null;
            if (email == null) {
                email = "whiterabbit.nsk@gmail.com";             // todo: refactor
            }
            for (User user : users) {
                if (email.equals(user.getEmail())) {
                    result = user;
                    break;
                }
            }
            return convertToJson(result);
        };
    }

    private Function<String, String> getPassword() {
        return email -> {
            LOG.info("***validation | getPassword***");
            LOG.info(email);
            final List<User> users = DAO.getAll("from User");
            String password = null;
            for (User user : users) {
                if (email.equals(user.getEmail())) {
                    password = user.getPassword();
                    break;
                }
            }
            return password;
        };
    }

    private Function<String, String> getBrands() {
        return str -> {
            LOG.info("***validation | getBrands***");
            return convertToJson(DAO.getAll("from Brand"));
        };
    }

    private Function<String, String> getModels() {
        return brandId -> {
            LOG.info("***validation | getModels***");
            final List<Model> models = DAO.getAll("from Model");
            final List<Model> result = new ArrayList<>();
            for (Model model : models) {
                if (Integer.parseInt(brandId) == model.getBrand().getId()) {
                    result.add(model);
                    System.out.println(model.getName());
                }
            }
            return convertToJson(result);
        };
    }

    private Function<String, String> getColors() {
        return str -> {
            LOG.info("***validation | getColors***");
            return convertToJson(DAO.getAll("from Color"));
        };
    }
}
