package ru.job4j.xml;


/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 28.07.18
 */

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Генерирует XML из данных БД.
 */
public class StoreXML {
    /**
     * файл, куда будем сохранять данные
     */
    private final File target;

    public StoreXML(File target) {
        this.target = target;
    }

    /**
     * сохраняет данные из list в файл target.
     * @param list
     */
    public void save(List<Entry> list) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Entries.class);
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        Entries entries = new Entries();
        entries.setEntries(list);
        marshaller.marshal(entries, this.target);
        marshaller.marshal(entries, System.out);
    }


    public List<Entry> getValues(String config) {
        List<Entry> list = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(config);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM entry;");
            while (rs.next()) {
                Entry entry = new Entry(rs.getInt("field"));
                list.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @XmlRootElement
    public static class Entry {
        private int value;

        public Entry(){}

        public Entry(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    @XmlRootElement
    public static class Entries {
        private List<Entry> list;

        public void setEntries(List<Entry> listNew) {
            this.list = listNew;
        }

        @XmlElement(name="myEntry")
        public List<Entry> getList() {
            return list;
        }
    }


}
