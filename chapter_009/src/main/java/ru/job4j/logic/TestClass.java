package ru.job4j.logic;

import ru.job4j.models.*;

import java.util.ArrayList;
import java.util.List;

public class TestClass {
    private static final HibernateManager FACTORY = HibernateManager.getInstance();


    public int addOrUpdate(Object obj) {
        return FACTORY.transaction(session -> {
            session.saveOrUpdate(obj);
            return 0;
        });
    }

    public int delete(Object obj) {
        return FACTORY.transaction(session -> {
            session.delete(obj);
            return 0;
        });
    }

    public int deleteCar(int id) {
        return FACTORY.transaction(session -> {
            CarXML car = session.get(CarXML.class, id);
            session.delete(car);
            return 0;
        });
    }

    public <T> List<T> getAll(String str) {
        return FACTORY.transaction(session -> {
            List<T> list = new ArrayList<>();
            list.addAll(session.createQuery(str).list());
            return list;
        });
    }

    public static void main(String[] args) {
        TestClass test = new TestClass();
        CarcaseAnts carcase = new CarcaseAnts("sedan");
        EngineAnts engine = new EngineAnts("electric");
        TransmissionAnts transmission = new TransmissionAnts("none");
        CarAnts car = new CarAnts("tesla model 3", carcase, engine,
                transmission);
        test.addOrUpdate(carcase);
        test.addOrUpdate(engine);
        test.addOrUpdate(transmission);
        test.addOrUpdate(car);

        CarcaseXML carcaseXML = new CarcaseXML("crossover");
        EngineXML engineXML = new EngineXML("electric");
        TransmissionXML transmissionXML = new TransmissionXML("none");
        CarXML carXML = new CarXML("tesla model x", carcaseXML, engineXML, transmissionXML);
        test.addOrUpdate(carcaseXML);
        test.addOrUpdate(engineXML);
        test.addOrUpdate(transmissionXML);
        test.addOrUpdate(carXML);

        List<CarXML> cars = test.getAll("from CarXML");
        for (CarXML el : cars) {
            System.out.println(el.getName() + el.getEngine().getName());
        }
    }
}
