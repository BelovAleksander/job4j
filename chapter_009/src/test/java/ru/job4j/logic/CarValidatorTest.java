package ru.job4j.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.cars.logic.CarValidator;
import ru.job4j.cars.models.*;

import java.util.HashMap;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 07.10.18
 */

@Ignore
public class CarValidatorTest {
    private CarValidator validator = CarValidator.getInstance();


    private HashMap<String, String> createCommand(String action, String value) {
        HashMap<String, String> result = new HashMap<>();
        result.put("action", action);
        result.put("value", value);
        return result;
    }

    @Test
    public void whenAddCarcaseXML() {
        String carcase = validator.execute(
                createCommand("addCarcaseXML", "sedan"));
        String carcases = validator.execute(
                createCommand("getCarcasesXML", ""));

        assertTrue(carcases.contains(carcase));
    }

    @Test
    public void whenAddCarcaseAnts() {
        String carcase = validator.execute(
                createCommand("addCarcase@", "sedan"));
        String carcases = validator.execute(
                createCommand("getCarcases@", "")
        );

        assertThat(carcases.contains(carcase), is(true));
    }

    @Test
    public void whenAddEngineXML() {
        String engine = validator.execute(
                createCommand("addEngineXML", "electric")
        );
        String engines = validator.execute(
                createCommand("getEnginesXML", "")
        );

        assertThat(engines.contains(engine), is(true));
    }

    @Test
    public void whenAddEngineAnts() {
        String engine = validator.execute(
                createCommand("addEngine@", "diesel")
        );
        String engines = validator.execute(
                createCommand("getEngines@", "")
        );

        assertThat(engines.contains(engine), is(true));
    }

    @Test
    public void whenAddTransmissionXML() {
        String transmission = validator.execute(
                createCommand("addTransmissionXML", "mechanic")
        );
        String transmissions = validator.execute(
                createCommand("getTransmissionsXML", "")
        );

        assertThat(transmissions.contains(transmission), is(true));
    }

    @Test
    public void whenAddTransmissionAnts() {
        String transmission = validator.execute(
                createCommand("addTransmission@", "robot")
        );
        String transmissions = validator.execute(
                createCommand("getTransmissions@", "")
        );

        assertThat(transmissions.contains(transmission), is(true));
    }

    @Test
    public void whenAddCarXML() throws JsonProcessingException {
        ObjectMapper converter = new ObjectMapper();

        validator.execute(
                createCommand("addCarcaseXML", "sedan")
        );
        validator.execute(
                createCommand("addEngineXML", "electric")
        );
        validator.execute(
                createCommand("addTransmissionXML", "none")
        );

        CarcaseXML carcase = new CarcaseXML("sedan");
        carcase.setId(1);
        EngineXML engine = new EngineXML("electric");
        engine.setId(1);
        TransmissionXML transmission = new TransmissionXML("none");
        transmission.setId(1);
        CarXML carXML = new CarXML("tesla", carcase, engine, transmission);

        String carJSON = converter.writerWithDefaultPrettyPrinter().writeValueAsString(carXML);
        String car = validator.execute(
                createCommand("addCarXML", carJSON)
        );
        String cars = validator.execute(
                createCommand("getCarsXML", "")
        );

        assertThat(car.equals(""), is(false));
        assertThat(cars.contains(car), is(true));
    }

    @Test
    public void whenAddCarAnts() throws JsonProcessingException {
        ObjectMapper converter = new ObjectMapper();

        validator.execute(
                createCommand("addCarcase@", "sedan")
        );
        validator.execute(
                createCommand("addEngine@", "electric")
        );
        validator.execute(
                createCommand("addTransmission@", "none")
        );

        CarcaseAnts carcase = new CarcaseAnts("sedan");
        carcase.setId(1);
        EngineAnts engine = new EngineAnts("electric");
        engine.setId(1);
        TransmissionAnts transmission = new TransmissionAnts("none");
        transmission.setId(1);
        CarAnts carAnts = new CarAnts("tesla", carcase, engine, transmission);

        String carJSON = converter.writerWithDefaultPrettyPrinter().writeValueAsString(carAnts);
        String car = validator.execute(
                createCommand("addCar@", carJSON)
        );
        String cars = validator.execute(
                createCommand("getCars@", "")
        );

        assertThat(car.equals(""), is(false));
        assertThat(cars.contains(car), is(true));
    }

    @Test
    public void whenUpdateCarcaseXML() throws JsonProcessingException {
        CarcaseXML carcaseXML = new CarcaseXML("crossover");
        carcaseXML.setId(1);
        String carcaseJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(carcaseXML);

        String oldValue = validator.execute(createCommand("addCarcaseXML", "sedan"));
        String updatedValue = validator.execute(createCommand("updateCarcaseXML", carcaseJSON));
        String carcases = validator.execute(createCommand("getCarcasesXML", ""));

        assertThat(carcases.contains(updatedValue), is(true));
        assertThat(carcases.contains(oldValue), is(false));
    }

    @Test
    public void whenUpdateCarcaseAnts() throws JsonProcessingException {
        CarcaseAnts carcaseAnts = new CarcaseAnts("crossover");
        carcaseAnts.setId(1);
        String carcaseJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(carcaseAnts);

        String oldValue = validator.execute(
                createCommand("addCarcase@", "sedan")
        );
        String updatedValue = validator.execute(
                createCommand("updateCarcase@", carcaseJSON)
        );
        String carcases = validator.execute(
                createCommand("getCarcases@", "")
        );

        assertThat(carcases.contains(updatedValue), is(true));
        assertThat(carcases.contains(oldValue), is(false));
    }

    @Test
    public void whenUpdateEngineXML() throws JsonProcessingException {
        EngineXML engineXML = new EngineXML("electric");
        engineXML.setId(1);
        String engineJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(engineXML);

        String oldValue = validator.execute(
                createCommand("addEngineXML", "diesel")
        );
        String updatedValue = validator.execute(
                createCommand("updateEngineXML", engineJSON)
        );
        String engines = validator.execute(
                createCommand("getEnginesXML", "")
        );

        assertThat(engines.contains(updatedValue), is(true));
        assertThat(engines.contains(oldValue), is(false));
    }

    @Test
    public void whenUpdateEngineAnts() throws JsonProcessingException {
        EngineAnts engineAnts = new EngineAnts("electric");
        engineAnts.setId(1);
        String engineJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(engineAnts);

        String oldValue = validator.execute(
                createCommand("addEngine@", "diesel")
        );
        String updatedValue = validator.execute(
                createCommand("updateEngine@", engineJSON)
        );
        String engines = validator.execute(
                createCommand("getEngines@", "")
        );

        assertThat(engines.contains(updatedValue), is(true));
        assertThat(engines.contains(oldValue), is(false));
    }

    @Test
    public void whenUpdateTransmissionXML() throws JsonProcessingException {
        TransmissionXML transmissionXML = new TransmissionXML("none");
        transmissionXML.setId(1);
        String transmissionJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(transmissionXML);

        String oldValue = validator.execute(
                createCommand("addTransmissionXML", "mechanic")
        );
        String updatedValue = validator.execute(
                createCommand("updateTransmissionXML", transmissionJSON)
        );
        String transmissions = validator.execute(
                createCommand("getTransmissionsXML", "")
        );

        assertThat(transmissions.contains(updatedValue), is(true));
        assertThat(transmissions.contains(oldValue), is(false));
    }

    @Test
    public void whenUpdateTransmissionAnts() throws JsonProcessingException {
        TransmissionAnts transmission = new TransmissionAnts("none");
        transmission.setId(1);
        String transmissionJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(transmission);

        String oldValue = validator.execute(
                createCommand("addTransmission@", "mechanic")
        );
        String updatedValue = validator.execute(
                createCommand("updateTransmission@", transmissionJSON)
        );
        String transmissions = validator.execute(
                createCommand("getTransmissions@", "")
        );

        assertThat(transmissions.contains(updatedValue), is(true));
        assertThat(transmissions.contains(oldValue), is(false));
    }

    @Test
    public void whenUpdateCarXML() throws JsonProcessingException {
        CarcaseXML carcase = new CarcaseXML("sedan");
        carcase.setId(1);
        EngineXML engine = new EngineXML("electric");
        engine.setId(1);
        TransmissionXML transmission = new TransmissionXML("none");
        transmission.setId(1);
        CarXML carXML = new CarXML("tesla", carcase, engine, transmission);
        String carJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(carXML);

        validator.execute(
                createCommand("addCarcaseXML", "sedan")
        );
        validator.execute(
                createCommand("addEngineXML", "electric")
        );
        validator.execute(
                createCommand("addTransmissionXML", "none")
        );
        String oldCar = validator.execute(
                createCommand("addCarXML", carJSON)
        );
        carcase = new CarcaseXML("crossover");
        carcase.setId(2);
        validator.execute(
                createCommand("addCarcaseXML", "crossover")
        );

        carXML = new CarXML("tesla model x", carcase, engine, transmission);
        carXML.setId(1);
        carJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(carXML);

        String newCar = validator.execute(
                createCommand("addCarXML", carJSON)
        );
        String cars = validator.execute(
                createCommand("getCarsXML", "")
        );

        assertThat(cars.contains(newCar), is(true));
        assertThat(cars.contains(oldCar), is(false));
    }

    @Test
    public void whenUpdateCarAnts() throws JsonProcessingException {
        CarcaseAnts carcase = new CarcaseAnts("sedan");
        carcase.setId(1);
        EngineAnts engine = new EngineAnts("electric");
        engine.setId(1);
        TransmissionAnts transmission = new TransmissionAnts("none");
        transmission.setId(1);
        CarAnts carAnts = new CarAnts("tesla", carcase, engine, transmission);
        String carJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(carAnts);

        validator.execute(
                createCommand("addCarcase@", "sedan")
        );
        validator.execute(
                createCommand("addEngine@", "electric")
        );
        validator.execute(
                createCommand("addTransmission@", "none")
        );
        String oldCar = validator.execute(
                createCommand("addCar@", carJSON)
        );

        carcase = new CarcaseAnts("crossover");
        carcase.setId(2);
        validator.execute(
                createCommand("addCarcase@", "crossover")
        );
        carAnts = new CarAnts("tesla model x", carcase, engine, transmission);
        carAnts.setId(1);
        carJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(carAnts);

        String newCar = validator.execute(
                createCommand("addCar@", carJSON)
        );
        String cars = validator.execute(
                createCommand("getCars@", "")
        );

        assertThat(cars.contains(newCar), is(true));
        assertThat(cars.contains(oldCar), is(false));
    }

    @Test
    public void whenDeleteCarcaseXML() {
        String first = validator.execute(
                createCommand("addCarcaseXML", "sedan")
        );
        String second =  validator.execute(
                createCommand("addCarcaseXML", "universal")
        );
        validator.execute(
                createCommand("deleteCarcaseXML", "1")
        );
        String carcases = validator.execute(
                createCommand("getCarcasesXML", "")
        );

        assertThat(carcases.contains(second) && !carcases.contains(first), is(true));
    }

    @Test
    public void whenDeleteCarcaseAnts() {
        String first = validator.execute(
                createCommand("addCarcase@", "sedan")
        );
        String second = validator.execute(
                createCommand("addCarcase@", "universal")
        );
        validator.execute(
                createCommand("deleteCarcase@", "2")
        );
        String carcases = validator.execute(
                createCommand("getCarcases@", "")
        );
        assertThat(carcases.contains(first) && !carcases.contains(second), is(true));
    }

    @Test
    public void whenDeleteEngineXML() {
        String first = validator.execute(
                createCommand("addEngineXML", "diesel")
        );
        String second = validator.execute(
                createCommand("addEngineXML", "electric")
        );
        validator.execute(
                createCommand("deleteEngineXML", "1")
        );
        String engines = validator.execute(
                createCommand("getEnginesXML", "")
        );
        assertThat(engines.contains(second) && !engines.contains(first), is(true));
    }

    @Test
    public void whenDeleteEngineAnts() {
        String first = validator.execute(
                createCommand("addEngine@", "diesel")
        );
        String second = validator.execute(
                createCommand("addEngine@", "electric")
        );
        validator.execute(
                createCommand("deleteEngine@", "1")
        );
        String engines = validator.execute(
                createCommand("getEngines@", "")
        );
        assertThat(engines.contains(second) && !engines.contains(first), is(true));
    }

    @Test
    public void whenDeleteTransmissionXML() {
        String first = validator.execute(
                createCommand("addTransmissionXML", "mechanic")
        );
        String second = validator.execute(
                createCommand("addTransmissionXML", "robot")
        );
        validator.execute(
                createCommand("deleteTransmissionXML", "1")
        );
        String transmissions = validator.execute(
                createCommand("getTransmissionsXML", "")
        );
        assertThat(transmissions.contains(second) && !transmissions.contains(first), is(true));
    }

    @Test
    public void whenDeleteTransmissionAnts() {
        String first = validator.execute(
                createCommand("addTransmission@", "mechanic")
        );
        String second = validator.execute(
                createCommand("addTransmission@", "robot")
        );
        validator.execute(
                createCommand("deleteTransmission@", "1")
        );
        String transmissions = validator.execute(
                createCommand("getTransmissions@", "")
        );
        assertThat(transmissions.contains(second) && !transmissions.contains(first), is(true));
    }

    @Test
    public void whenDeleteCarXML() throws JsonProcessingException {
        CarcaseXML carcase = new CarcaseXML("sedan");
        carcase.setId(1);
        EngineXML engine = new EngineXML("electric");
        engine.setId(1);
        TransmissionXML transmission = new TransmissionXML("none");
        transmission.setId(1);
        CarXML carXML = new CarXML("tesla", carcase, engine, transmission);
        String carJSON = new ObjectMapper().writeValueAsString(carXML);

        validator.execute(
                createCommand("addCarcaseXML", "sedan")
        );
        validator.execute(
                createCommand("addEngineXML", "electric")
        );
        validator.execute(
                createCommand("addTransmissionXML", "none")
        );
        String first = validator.execute(
                createCommand("addCarXML", carJSON)
        );
        carXML = new CarXML("bmw", carcase, engine, transmission);
        carJSON = new ObjectMapper().writeValueAsString(carXML);

        String second = validator.execute(
                createCommand("addCarXML", carJSON)
        );
        validator.execute(
                createCommand("deleteCarXML", "1")
        );
        String cars = validator.execute(
                createCommand("getCarsXML", "")
        );
        assertThat(cars.contains(second) && !cars.contains(first), is(true));
    }

    @Test
    public void whenDeleteCarAnts() throws JsonProcessingException {
        CarcaseAnts carcase = new CarcaseAnts("sedan");
        carcase.setId(1);
        EngineAnts engine = new EngineAnts("electric");
        engine.setId(1);
        TransmissionAnts transmission = new TransmissionAnts("none");
        transmission.setId(1);
        CarAnts carAnts = new CarAnts("tesla", carcase, engine, transmission);
        String carJSON = new ObjectMapper().writeValueAsString(carAnts);

        validator.execute(
                createCommand("addCarcase@", "sedan")
        );
        validator.execute(
                createCommand("addEngine@", "electric")
        );
        validator.execute(
                createCommand("addTransmission@", "none")
        );
        String first = validator.execute(
                createCommand("addCar@", carJSON)
        );
        carAnts = new CarAnts("bmw", carcase, engine, transmission);
        carJSON = new ObjectMapper().writeValueAsString(carAnts);

        String second = validator.execute(
                createCommand("addCar@", carJSON)
        );
        validator.execute(
                createCommand("deleteCar@", "1")
        );
        String cars = validator.execute(
                createCommand("getCars@", "")
        );
        assertThat(cars.contains(second) && !cars.contains(first), is(true));
    }
}