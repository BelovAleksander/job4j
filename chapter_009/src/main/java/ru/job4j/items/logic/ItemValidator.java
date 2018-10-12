package ru.job4j.items.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import ru.job4j.items.models.Item;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 30.09.18
 */
public class ItemValidator {
    private static final ItemValidator INSTANCE = new ItemValidator();
    private static final ItemStorage STORAGE = ItemStorage.getInstance();
    private static final ObjectMapper CONVERTER = new ObjectMapper();
    private static final ConcurrentHashMap<String, Function<String, String>> ACTIONS = new ConcurrentHashMap<>();
    private static final Logger LOG = Logger.getLogger("APP1");

    private ItemValidator() {
        ACTIONS.put("getAllItems", getAllItems());
        ACTIONS.put("getUnperformedItems", getAllUnperformedItems());
        ACTIONS.put("addItem", add());
        ACTIONS.put("changePerformance", changePerformance());
    }
    public static ItemValidator getInstance() {
        return INSTANCE;
    }

    private Function<String, String> add() {
        return description -> {
            LOG.info("validation | adding");
            final Item item = STORAGE.add(new Item(description));
            String json = null;
            try {
                json = CONVERTER.writerWithDefaultPrettyPrinter().writeValueAsString(item);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return json;
        };
    }

    private Function<String, String> getAllItems() {
        return str -> {
            LOG.info("validation | getting all items");
            final List<Item> items = STORAGE.getAllItems();
            String json = null;
            try {
                json = CONVERTER.writerWithDefaultPrettyPrinter().writeValueAsString(items);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return json;
        };
    }

    private Function<String, String> getAllUnperformedItems() {
        return str -> {
            LOG.info("validation | getting unperformed");
            final List<Item> items = STORAGE.getAllUnperformedItems();
            String json = null;
            try {
                json = CONVERTER.writerWithDefaultPrettyPrinter().writeValueAsString(items);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return json;
        };
    }

    private Function<String, String> changePerformance() {
        return id -> {
            STORAGE.changePerformance(Integer.parseInt(id));
            return "";
        };
    }

    public String execute(final HashMap<String, String> parameters) {
        LOG.info("validation | executing");
        LOG.info(parameters.get("action") + " | " + parameters.get("value"));
        return ACTIONS.get(parameters.get("action")).apply(parameters.get("value"));
    }
}
