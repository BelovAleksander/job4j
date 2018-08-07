package ru.job4j.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 07.08.18
 */

public class Manipulator {
    private final StoreSQL storeSQL;
    private final StoreXML storeXML;
    private final int count;

    public Manipulator(int count) {
        ResourceBundle resource = ResourceBundle.getBundle("xml");
        String createTable = resource.getString("createTableScript");
        String deleteFromTable = resource.getString("deleteScript");
        String psInsert = resource.getString("preparedStScript");
        String pullOut = resource.getString("pullOutScript");
        String url = resource.getString("urlForSQLite");

        this.storeSQL = new StoreSQL(url, createTable, deleteFromTable, psInsert, pullOut);
        this.storeXML = new StoreXML(new File("target.xml"));
        this.count = count;
    }

    public void init() {
        try {
            this.storeSQL.generate(this.count);
            List<StoreXML.Entry> list = storeSQL.getValues();
            storeXML.save(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        File dest = new File("dest.xsl");
        File schema = new File("schema.xsl");
        File target = new File("target.xml");
        convert(target, dest, schema);

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            SAXParser saxParser = factory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(new SAXHandler());
            xmlReader.parse("dest.xsl");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void convert(File source, File dest, File schema) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(schema));
            transformer.transform(new StreamSource(source), new StreamResult(dest));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    class SAXHandler extends DefaultHandler {
        private int element = 0;
        private int result = 0;
        @Override
        public void startDocument() throws SAXException {
            System.out.println("Start Parsing...");
        }
        @Override
        public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {
            if (qName.equals("entry")) {
                result += element;
                element++;
            }
        }
        @Override
        public void endDocument() {
            System.out.println("Stop parse XML...");
            System.out.println(result);
        }
    }
}
