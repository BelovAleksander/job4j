package ru.job4j.xml;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 30.07.18
 */

public class Manipulator {

    public void init(int count) {
        StoreSQL storeSQL = new StoreSQL("jdbc:sqlite:test.db");
        storeSQL.generate(count);

        File target = new File("target.xml");
        StoreXML storeXML = new StoreXML(target);
        List<StoreXML.Entry> list = storeXML.getValues("jdbc:sqlite:test.db");
        try {
            storeXML.save(list);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        String schema = "<xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"1.0\">\n" +
                "<xsl:template match=\"/\">\n" +
                "<entries>" +
                "   <xsl:for-each select=\"entries/myEntry\">\n" +
                "       <entry>" +
                "           <xsl:attribute name=\"href\">" +
                "               <xsl:value-of select=\"value\"/>" +
                "           </xsl:attribute>" +
                "       </entry>\n" +
                "   </xsl:for-each>\n" +
                " </entries>\n" +
                "</xsl:template>\n" +
                "</xsl:stylesheet>\n";
        ConvertXSLT converter = new ConvertXSLT();
        File dest = new File("dest.xsl");
        converter.convert(target, dest, schema);

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            SAXParser saxParser = factory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(new SAXPars());
            xmlReader.parse("dest.xsl");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}