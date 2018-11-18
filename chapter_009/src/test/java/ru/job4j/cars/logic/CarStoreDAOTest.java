package ru.job4j.cars.logic;

import org.junit.Test;
import ru.job4j.cars.models.CarXML;
import ru.job4j.cars.models.CarcaseXML;
import ru.job4j.cars.models.EngineXML;
import ru.job4j.cars.models.TransmissionXML;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CarStoreDAOTest {
    private CarStoreDAO dao = CarStoreDAO.getInstance();

    @Test
    public void whenAdd() {
        CarcaseXML carcase = new CarcaseXML("sedan");
        dao.addOrUpdate(carcase);
        List<CarcaseXML> carcases =  dao.getAll("from CarcaseXML");
        assertThat(carcases.get(0).getName(), is("sedan"));
    }

    @Test
    public void whenUpdate() {
        TransmissionXML old = new TransmissionXML("mechanic");
        dao.addOrUpdate(old);
        TransmissionXML fresh = new TransmissionXML("none");
        fresh.setId(1);
        dao.addOrUpdate(fresh);
        List<TransmissionXML> transmissions = dao.getAll("from TransmissionXML");
        assertThat(transmissions.get(0).getName(), is("none"));
    }

    @Test
    public void whenDelete() {
        EngineXML engine = new EngineXML("diesel");
        dao.addOrUpdate(engine);
        engine.setId(1);
        dao.delete(engine);
        assertThat(dao.getAll("from EngineXML").isEmpty(), is(true));
    }

    @Test
    public void whenDeleteCar() {
        CarXML car = new CarXML("test", null, null, null);
        dao.addOrUpdate(car);
        dao.deleteCar(1);
        assertThat(dao.getAll("from CarXML").isEmpty(), is(true));
    }
}