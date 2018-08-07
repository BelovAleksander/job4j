package ru.job4j.xml;
/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 07.08.18
 */

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.List;
/**
 * Генерирует XML из данных БД.
 */
public class StoreXML {
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
    }
    @XmlRootElement
    public static class Entry {
        private int value;

        public Entry() {
        }
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
        @XmlElement(name = "myEntry")
        public List<Entry> getList() {
            return list;
        }
    }
}
