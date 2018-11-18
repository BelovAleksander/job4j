package ru.job4j.cars.logic;

import ru.job4j.cars.models.CarXML;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 06.10.18
 */
public class CarStoreDAO {
    private static final CarStoreDAO INSTANCE = new CarStoreDAO();
    private static final HibernateManager FACTORY = HibernateManager.getInstance("hibernate.cfg.xml");

    private CarStoreDAO() {

    }

    public static CarStoreDAO getInstance() {
        return INSTANCE;
    }

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
}
