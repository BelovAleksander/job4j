package ru.job4j.xml;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 29.07.18
 */

public class ConvertXSLT {
    public void convert(File source, File dest, String schema) {
        try {

            BufferedReader reader = Files.newBufferedReader(Paths.get(source.getAbsolutePath()));
            StringBuilder builderXML = new StringBuilder();
            reader.lines().forEach(s -> builderXML.append(s).append('\n'));
            String xml = builderXML.toString();

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(
                    new StreamSource(
                            new ByteArrayInputStream(schema.getBytes()))
            );

            transformer.transform(new StreamSource(
                    new ByteArrayInputStream(xml.getBytes())), new StreamResult(dest));


        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
