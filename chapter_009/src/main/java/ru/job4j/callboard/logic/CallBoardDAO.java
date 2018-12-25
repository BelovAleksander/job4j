package ru.job4j.callboard.logic;

import org.hibernate.Filter;
import org.hibernate.Session;
import ru.job4j.callboard.models.Advert;
import ru.job4j.cars.logic.HibernateManager;

import java.util.ArrayList;
import java.util.HashMap;
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

    public List<Advert> getAdverts(final HashMap<String, HashMap<String, Integer>> filters) {
        final Session session = FACTORY.getSession();
        if (filters != null) {
            enableFilters(filters, session);
        }
        List<Advert> adverts = session.createQuery("from Advert").getResultList();
        disableFilters(filters, session);
        session.close();
        return adverts;
    }

    private void disableFilters(final HashMap<String, HashMap<String, Integer>> filters, final Session session) {
        if (filters != null) {
            for (String filterName : filters.keySet()) {
                session.disableFilter(filterName);
            }
        }
    }

    private void enableFilters(final HashMap<String, HashMap<String, Integer>> filters, final Session session) {
        for (String filterName : filters.keySet()) {
            HashMap<String, Integer> parameters = filters.get(filterName);
            Filter filter = session.enableFilter(filterName);
            if (parameters != null) {
                for (String param : parameters.keySet()) {
                    int value = Integer.parseInt(parameters.get(param) + "");
                    filter.setParameter(param, value);
                }
            }
        }
    }

    public Object getObjectById(final Class objClass, final int id) {
        return FACTORY.transaction(session -> {
            final Object obj = session.get(objClass, id);
            return obj;
        });
    }

    public List<Advert> limitByBrand(final int brandId) {
        final Session session = FACTORY.getSession();
        final Filter filter = session.enableFilter("limitByBrand");
        filter.setParameter("currentBrandId", brandId);
        final List<Advert> list = new ArrayList<>();
        list.addAll(session.createQuery("from Advert").getResultList());
        session.disableFilter("limitByBrand");
        session.close();
        return list;
    }
}
