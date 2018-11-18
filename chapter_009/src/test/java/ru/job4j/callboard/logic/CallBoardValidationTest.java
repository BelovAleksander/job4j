package ru.job4j.callboard.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import ru.job4j.callboard.models.Advert;
import ru.job4j.callboard.models.User;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * @author Belov Aleksandr (whiterabbit.nsk@gmail.com)
 * @since 18.11.2018
 */

public class CallBoardValidationTest {
    private final static CallBoardValidation VALIDATION = CallBoardValidation.getInstance();
    private static final ObjectMapper CONVERTER = new ObjectMapper();

    private HashMap<String, String> createCommand(final String action, final String value) {
        HashMap<String, String> command = new HashMap<>();
        command.put("action", action);
        command.put("value", value);
        return command;
    }

    @Test
    public void whenAddAdvert() throws IOException {
        final Advert input = new Advert(111);
        input.setTitle("test");
        String json = CONVERTER.writerWithDefaultPrettyPrinter().writeValueAsString(input);
        VALIDATION.execute(createCommand("addAdvert", json));
        json = VALIDATION.execute(createCommand("getAdvert", "111"));
        final Advert output = CONVERTER.readValue(json, Advert.class);
        assertEquals(output.getTitle(), "test");
    }

    @Test
    public void whenUpdateAdvert() throws IOException {
        final Advert old = new Advert(111);
        old.setTitle("old");
        String json = CONVERTER.writerWithDefaultPrettyPrinter().writeValueAsString(old);
        VALIDATION.execute(createCommand("addAdvert", json));
        final Advert fresh = new Advert(111);
        fresh.setTitle("fresh");
        json = CONVERTER.writerWithDefaultPrettyPrinter().writeValueAsString(fresh);
        VALIDATION.execute(createCommand("updateAdvert", json));
        json = VALIDATION.execute(createCommand("getAdvert", "111"));
        final Advert result = CONVERTER.readValue(json, Advert.class);
        assertEquals(result.getTitle(), "fresh");
    }

    @Test
    public void whenDeleteAdvert() throws JsonProcessingException {
        final Advert advert = new Advert(111);
        String json = CONVERTER.writerWithDefaultPrettyPrinter().writeValueAsString(advert);
        VALIDATION.execute(createCommand("addAdvert", json));
        final String afterAdding = VALIDATION.execute(createCommand("getAdvert", "111"));
        VALIDATION.execute(createCommand("deleteAdvert", "111"));
        assertTrue(!VALIDATION.execute(createCommand("getAdverts", "")).contains(afterAdding));
    }

    @Test
    public void whenGetAllAdverts() throws JsonProcessingException {
        final Advert advert = new Advert(111);
        String json = CONVERTER.writerWithDefaultPrettyPrinter().writeValueAsString(advert);
        VALIDATION.execute(createCommand("addAdvert", json));
        assertTrue(VALIDATION.execute(createCommand("getAdverts", "")).contains(json));
    }

    @Test
    public void whenChangeStatus() throws IOException {
        Advert advert = new Advert(111);
        advert.setStatus(true);
        String json = CONVERTER.writerWithDefaultPrettyPrinter().writeValueAsString(advert);
        VALIDATION.execute(createCommand("addAdvert", json));
        VALIDATION.execute(createCommand("changeStatus", "111"));
        json = VALIDATION.execute(createCommand("getAdvert", "111"));
        advert = CONVERTER.readValue(json, Advert.class);
        assertFalse(advert.isStatus());
    }

    @Test
    public void whenGetPhoto() throws JsonProcessingException {
        Advert advert = new Advert(111);
        advert.setPhoto("photo");
        String json = CONVERTER.writerWithDefaultPrettyPrinter().writeValueAsString(advert);
        VALIDATION.execute(createCommand("addAdvert", json));
        String result = VALIDATION.execute(createCommand("getPhoto", "111"));
        assertEquals(result, "photo");
    }

    @Test
    public void whenAddUser() throws IOException {
        User input = new User("test", "password", null);
        String json = CONVERTER.writerWithDefaultPrettyPrinter().writeValueAsString(input);
        VALIDATION.execute(createCommand("addUser", json));
        json = VALIDATION.execute(createCommand("getUser", "test"));
        User output = CONVERTER.readValue(json, User.class);
        assertEquals(output.getEmail(), input.getEmail());
    }

    @Test
    public void whenGetPassword() throws JsonProcessingException {
        User user = new User("test", "password", null);
        String json = CONVERTER.writerWithDefaultPrettyPrinter().writeValueAsString(user);
        VALIDATION.execute(createCommand("addUser", json));
        String password = VALIDATION.execute(createCommand("getPassword", "test"));
        assertEquals(password, user.getPassword());
    }
}
