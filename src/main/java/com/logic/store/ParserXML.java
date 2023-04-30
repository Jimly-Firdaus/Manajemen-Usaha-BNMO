package com.logic.store;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.logic.store.interfaces.Parseable;

import lombok.AllArgsConstructor;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

@AllArgsConstructor
public class ParserXML implements Parseable {
    private String filename;

    /**
     * @brief Reads data from an XML file and returns it as a list.
     * @param type The class type of the data to be read.
     * @return A list of data read from the file.
     */
    public <T> List<T> readData(Class<T> type) {
        List<T> data = new ArrayList<>();

        try {
            File file = new File(filename);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            // Parse XML
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            // Get a list of elements with the tag name of the input type
            NodeList nodeList = doc.getElementsByTagName(type.getSimpleName());

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                T item = type.getDeclaredConstructor().newInstance();
                for (Field field : type.getDeclaredFields()) {
                    field.setAccessible(true);
                    String value = element.getElementsByTagName(field.getName()).item(0).getTextContent();
                    
                    // Set the value of the field based on its type
                    if (field.getType() == float.class) {
                        field.set(item, Float.parseFloat(value));
                    } else if (field.getType() == int.class) {
                        field.set(item, Integer.parseInt(value));
                    } else if (field.getType() == double.class) {
                        field.set(item, Double.parseDouble(value));
                    } else if (field.getType() == boolean.class) {
                        field.set(item, Boolean.parseBoolean(value));
                    } else {
                        field.set(item, value);
                    }
                }
                data.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    /**
     * @brief Writes a list of data to an XML file.
     * @param data The list of data to be written.
     */
    public <T> void writeData(List<T> data) {
        try {
            // Create XML
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            // Create root then append to document
            Element rootElement = doc.createElement("data");
            doc.appendChild(rootElement);

            for (T item : data) {
                Element element = doc.createElement(item.getClass().getSimpleName());
                for (Field field : item.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    Object value = field.get(item);
                    Element fieldElement = doc.createElement(field.getName());
                    fieldElement.appendChild(doc.createTextNode(value.toString()));
                    element.appendChild(fieldElement);
                }
                rootElement.appendChild(element);
            }
            
            // Write XML to file
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
