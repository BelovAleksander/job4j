package ru.job4j.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 30.07.18
 */

public class SAXPars extends DefaultHandler {
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
