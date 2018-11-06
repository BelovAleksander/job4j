package ru.job4j.callboard.logic;

import org.hibernate.Criteria;
import ru.job4j.callboard.models.Advert;
import ru.job4j.cars.logic.HibernateManager;
import ru.job4j.cars.models.CarXML;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 21.10.18
 */
public class CallBoardDAO {
    private static final CallBoardDAO INSTANCE = new CallBoardDAO();
    private static final HibernateManager FACTORY = HibernateManager.getInstance("/hibernateCB.cfg.xml");

    private CallBoardDAO() {

    }

    public static CallBoardDAO getInstance() {
        return INSTANCE;
    }

    public void addOrUpdate(final Object object) {
        FACTORY.transaction(session -> {
            session.saveOrUpdate(object);
            return null;
        });
    }

    public void deleteAdvert(final int id) {
        FACTORY.transaction(session -> {
            final Advert advert = session.get(Advert.class, id);
            session.delete(advert);
            return null;
        });
    }

    public <T> List<T> getAll(final String spec) {
        return FACTORY.transaction(session -> {
            final List<T> list = new ArrayList<>();
            list.addAll(session.createQuery(spec).list());
            return list;
        });
    }

    public Object getObjectById(final Class objClass, final int id) {
        return FACTORY.transaction(session -> {
            final Object obj = session.get(objClass, id);
            return obj;
        });
    }
}
