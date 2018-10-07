package ru.job4j.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.models.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.logging.Logger;
/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 05.10.18
 */
public class CarValidator {
    private static final CarValidator INSTANCE = new CarValidator();
    private final ConcurrentHashMap<String, Function<String, String>> actions = new ConcurrentHashMap<>();
    private static final CarStoreDAO DAO = CarStoreDAO.getInstance();
    private static final ObjectMapper CONVERTER = new ObjectMapper();
    private static final Logger LOG = Logger.getLogger("APP1");

    private CarValidator() {
        actions.put("addCarcase@", addCarcaseAnts());
        actions.put("addEngine@", addEngineAnts());
        actions.put("addTransmission@", addTransmissionAnts());
        actions.put("addCar@", addCarAnts());

        actions.put("addCarcaseXML", addCarcaseXML());
        actions.put("addEngineXML", addEngineXML());
        actions.put("addTransmissionXML", addTransmissionXML());
        actions.put("addCarXML", addCarXML());

        actions.put("updateCarcase@", updateCarcaseAnts());
        actions.put("updateEngine@", updateEngineAnts());
        actions.put("updateTransmission@", updateTransmissionAnts());
        actions.put("updateCar@", addCarAnts());

        actions.put("updateCarcaseXML", updateCarcaseXML());
        actions.put("updateEngineXML", updateEngineXML());
        actions.put("updateTransmissionXML", updateTransmissionXML());
        actions.put("updateCarXML", addCarXML());

        actions.put("deleteCarcaseXML", deleteCarcaseXML());
        actions.put("deleteEngineXML", deleteEngineXML());
        actions.put("deleteTransmissionXML", deleteTransmissionXML());
        actions.put("deleteCarXML", deleteCarXML());

        actions.put("deleteEngine@", deleteEngineAnts());
        actions.put("deleteCarcase@", deleteCarcaseAnts());
        actions.put("deleteTransmission@", deleteTransmissionAnts());
        actions.put("deleteCar@", deleteCarAnts());

        actions.put("getCarcasesXML", getCarcasesXML());
        actions.put("getEnginesXML", getEnginesXML());
        actions.put("getTransmissionsXML", getTransmissionsXML());
        actions.put("getCarsXML", getCarsXML());

        actions.put("getCarcases@", getCarcasesAnts());
        actions.put("getEngines@", getEnginesAnts());
        actions.put("getTransmissions@", getTransmissionsAnts());
        actions.put("getCars@", getCarsAnts());
    }

    public static CarValidator getInstance() {
        return INSTANCE;
    }


    public String execute(final HashMap<String, String> param) {
        LOG.info("action: " + param.get("action") + " value: " + param.get("value"));
        return actions.get(param.get("action")).apply(param.get("value"));
    }

    private String convert(Object obj) {
        String result = null;
        try {
            result = CONVERTER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Function<String, String> addCarcaseXML() {
        return name -> {
            CarcaseXML carcase = new CarcaseXML(name);
            DAO.addOrUpdate(carcase);
            return convert(carcase);
        };
    }

    private Function<String, String> addEngineXML() {
        return name -> {
            EngineXML engine = new EngineXML(name);
            DAO.addOrUpdate(engine);
            return convert(engine);
        };
    }

    private Function<String, String> addTransmissionXML() {
        return name -> {
            TransmissionXML transmission = new TransmissionXML(name);
            DAO.addOrUpdate(transmission);
            return convert(transmission);
        };
    }

    private Function<String, String> addCarXML() {
        return jsonString -> {
            CarXML car = null;
            try {
                car = CONVERTER.readValue(jsonString, CarXML.class);
                DAO.addOrUpdate(car);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return car == null ? "" : convert(car);
        };
    }

    private Function<String, String> addCarcaseAnts() {
        return name -> {
                CarcaseAnts carcase = new CarcaseAnts(name);
                DAO.addOrUpdate(carcase);
            return convert(carcase);
        };
    }

    private Function<String, String> addEngineAnts() {
        return name -> {
            EngineAnts engine = new EngineAnts(name);
            DAO.addOrUpdate(engine);
            return convert(engine);
        };
    }

    private Function<String, String> addTransmissionAnts() {
        return name -> {
            TransmissionAnts transmission = new TransmissionAnts(name);
            DAO.addOrUpdate(transmission);
            return convert(transmission);
        };
    }

    private Function<String, String> addCarAnts() {
        return jsonString -> {
            CarAnts car = null;
            try {
                car = CONVERTER.readValue(jsonString, CarAnts.class);
                DAO.addOrUpdate(car);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return car == null ? "" : convert(car);
        };
    }

    private Function<String, String> updateCarcaseXML() {
        return jsonString -> {
            CarcaseXML carcase = null;
            try {
                carcase = CONVERTER.readValue(jsonString, CarcaseXML.class);
                DAO.addOrUpdate(carcase);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return carcase == null ? "" : convert(carcase);
        };
    }

    private Function<String, String> updateEngineXML() {
        return jsonString -> {
            EngineXML engine = null;
            try {
                engine = CONVERTER.readValue(jsonString, EngineXML.class);
                DAO.addOrUpdate(engine);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return engine == null ? "" : convert(engine);
        };
    }

    private Function<String, String> updateTransmissionXML() {
        return jsonString -> {
            TransmissionXML transmission = null;
            try {
                transmission = CONVERTER.readValue(jsonString, TransmissionXML.class);
                DAO.addOrUpdate(transmission);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return transmission == null ? "" : convert(transmission);
        };
    }


    private Function<String, String> updateCarcaseAnts() {
        return jsonString -> {
            CarcaseAnts carcase = null;
            try {
                carcase = CONVERTER.readValue(jsonString, CarcaseAnts.class);
                DAO.addOrUpdate(carcase);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return carcase == null ? "" : convert(carcase);
        };
    }

    private Function<String, String> updateEngineAnts() {
        return jsonString -> {
            EngineAnts engine = null;
            try {
                engine = CONVERTER.readValue(jsonString, EngineAnts.class);
                DAO.addOrUpdate(engine);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return engine == null ? "" : convert(engine);
        };
    }

    private Function<String, String> updateTransmissionAnts() {
        return jsonString -> {
            TransmissionAnts transmission = null;
            try {
                transmission = CONVERTER.readValue(jsonString, TransmissionAnts.class);
                DAO.addOrUpdate(transmission);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return transmission == null ? "" : convert(transmission);
        };
    }

    private Function<String, String> deleteCarcaseXML() {
        return id -> {
            CarcaseXML carcase = new CarcaseXML();
            carcase.setId(Integer.parseInt(id));
            DAO.delete(carcase);
            return null;
        };
    }

    private Function<String, String> deleteEngineXML() {
        return id -> {
            EngineXML engine = new EngineXML();
            engine.setId(Integer.parseInt(id));
            DAO.delete(engine);
            return null;
        };
    }

    private Function<String, String> deleteTransmissionXML() {
        return id -> {
            TransmissionXML transmission = new TransmissionXML();
            transmission.setId(Integer.parseInt(id));
            DAO.delete(transmission);
            return null;
        };
    }

    private Function<String, String> deleteCarXML() {
        return id -> {
            DAO.deleteCar(Integer.parseInt(id));
            return null;
        };
    }

    private Function<String, String> deleteCarcaseAnts() {
        return id -> {
            CarcaseAnts carcase = new CarcaseAnts();
            carcase.setId(Integer.parseInt(id));
            DAO.delete(carcase);
            return null;
        };
    }

    private Function<String, String> deleteEngineAnts() {
        return id -> {
            EngineAnts engine = new EngineAnts();
            engine.setId(Integer.parseInt(id));
            DAO.delete(engine);
            return null;
        };
    }

    private Function<String, String> deleteTransmissionAnts() {
        return id -> {
            TransmissionAnts transmission = new TransmissionAnts();
            transmission.setId(Integer.parseInt(id));
            DAO.delete(transmission);
            return null;
        };
    }

    private Function<String, String> deleteCarAnts() {
        return id -> {
            DAO.deleteCar(Integer.parseInt(id));
            return null;
        };
    }

    private Function<String, String> getEnginesXML() {
        return str -> convert(DAO.getAll("from EngineXML"));
    }

    private Function<String, String> getCarcasesXML() {
        return str -> convert(DAO.getAll("from CarcaseXML"));
    }

    private Function<String, String> getTransmissionsXML() {
        return str -> convert(DAO.getAll("from TransmissionXML"));
    }

    private Function<String, String> getCarsXML() {
        return str -> convert(DAO.getAll("from CarXML"));
    }

    private Function<String, String> getCarcasesAnts() {
        return str -> convert(DAO.getAll("from CarcaseAnts"));
    }

    private Function<String, String> getEnginesAnts() {
        return str -> convert(DAO.getAll("from EngineAnts"));
    }

    private Function<String, String> getTransmissionsAnts() {
        return str -> convert(DAO.getAll("from TransmissionAnts"));
    }

    private Function<String, String> getCarsAnts() {
        return str -> convert(DAO.getAll("from CarAnts"));
    }
}
