package com.logic.store;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import lombok.AllArgsConstructor;
import lombok.Setter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

import com.logic.store.interfaces.Parseable;
import com.logic.feature.ListOfProduct;
import com.logic.feature.Product;

@AllArgsConstructor
@Setter
public class ParserXML implements Parseable {
    private String filename;

    /**
     * @brief Reads data from an XML file and returns it as a list.
     * @param type The class type of the data to be read.
     * @return A list of data read from the file.
     */
    public <T> List<T> readDatas(Class<T> type) {
        List<T> data = new ArrayList<>();

        try {
            File file = new File(this.filename);
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

                    if (field.getType().isPrimitive() || field.getType() == String.class) {
                        // Handle primitive types and String
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
                    } else if (field.getType() == ListOfProduct.class) {
                        // Handle ListOfProduct attribute
                        Element basketElement = (Element) element.getElementsByTagName(field.getName()).item(0);
                        NodeList productNodeList = basketElement.getElementsByTagName("product");

                        List<Product> productList = new ArrayList<>();
                        for (int j = 0; j < productNodeList.getLength(); j++) {
                            Element productElement = (Element) productNodeList.item(j);
                            Product product = new Product();

                            for (Field productField : Product.class.getDeclaredFields()) {
                                productField.setAccessible(true);
                                String value = productElement.getElementsByTagName(productField.getName()).item(0)
                                        .getTextContent();

                                // Set the value of the field based on its type
                                if (productField.getType() == float.class) {
                                    productField.set(product, Float.parseFloat(value));
                                } else if (productField.getType() == int.class) {
                                    productField.set(product, Integer.parseInt(value));
                                } else if (productField.getType() == double.class) {
                                    productField.set(product, Double.parseDouble(value));
                                } else if (productField.getType() == boolean.class) {
                                    productField.set(product, Boolean.parseBoolean(value));
                                } else {
                                    productField.set(product, value);
                                }
                            }

                            productList.add(product);
                        }

                        ListOfProduct listOfProduct = new ListOfProduct();
                        listOfProduct.setProductList(productList);
                        field.set(item, listOfProduct);
                    } else if (field.getType() == ArrayList.class || field.getType() == List.class) {
                        // Handle ArrayList and List types
                        NodeList list = element.getElementsByTagName(field.getName()).item(0).getChildNodes();
                        ParameterizedType listType = (ParameterizedType) field.getGenericType();
                        Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];
                        List<Object> objects = new ArrayList<>();
                        for (int j = 0; j < list.getLength(); j++) {
                            Node node = list.item(j);
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                Element elem = (Element) node;
                                Object obj = listClass.getDeclaredConstructor().newInstance();
                                for (Field f : listClass.getDeclaredFields()) {
                                    f.setAccessible(true);
                                    if (f.getType().isPrimitive() || f.getType() == String.class) {
                                        // Handle primitive types and String
                                        String value = elem.getElementsByTagName(f.getName()).item(0).getTextContent();
                                        // Set the value of the field based on its type
                                        if (f.getType() == float.class) {
                                            f.set(obj, Float.parseFloat(value));
                                        } else if (f.getType() == int.class) {
                                            f.set(obj, Integer.parseInt(value));
                                        } else if (f.getType() == double.class) {
                                            f.set(obj, Double.parseDouble(value));
                                        } else if (f.getType() == boolean.class) {
                                            f.set(obj, Boolean.parseBoolean(value));
                                        } else {
                                            f.set(obj, value);
                                        }
                                    } else if (f.getType() == ArrayList.class || f.getType() == List.class) {
                                        // Handle nested ArrayList and List types
                                        NodeList subList = elem.getElementsByTagName(f.getName()).item(0).getChildNodes();
                                        ParameterizedType subListType = (ParameterizedType) f.getGenericType();
                                        Class<?> subListClass = (Class<?>) subListType.getActualTypeArguments()[0];
                                        List<Object> subObjects = new ArrayList<>();
                                        for (int l = 0; l < subList.getLength(); l++) {
                                            Node subNode = subList.item(l);
                                            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                                                Element subElem = (Element) subNode;
                                                Object subObj = subListClass.getDeclaredConstructor().newInstance();
                                                for (Field sf : subListClass.getDeclaredFields()) {
                                                    sf.setAccessible(true);
                                                    if (sf.getType().isPrimitive() || sf.getType() == String.class) {
                                                        // Handle primitive types and String
                                                        String value = subElem.getElementsByTagName(sf.getName()).item(0).getTextContent();
                                                        // Set the value of the field based on its type
                                                        if (sf.getType() == float.class) {
                                                            sf.set(subObj, Float.parseFloat(value));
                                                        } else if (sf.getType() == int.class) {
                                                            sf.set(subObj, Integer.parseInt(value));
                                                        } else if (sf.getType() == double.class) {
                                                            sf.set(subObj, Double.parseDouble(value));
                                                        } else if (sf.getType() == boolean.class) {
                                                            sf.set(subObj, Boolean.parseBoolean(value));
                                                        } else {
                                                            sf.set(subObj, value);
                                                        }
                                                    }
                                                }
                                                subObjects.add(subObj);
                                            }
                                        }
                                        f.set(obj, subObjects);
                                    }
                                }
                                objects.add(obj);
                            }
                        }
                        field.set(item, objects);
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
    public <T> void writeDatas(List<T> data) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            // Create root element
            Element rootElement = doc.createElement("data");
            doc.appendChild(rootElement);

            for (T item : data) {
                // Create element for each item
                Element itemElement = doc.createElement(item.getClass().getSimpleName());
                rootElement.appendChild(itemElement);

                // Get all fields including superclass fields
                List<Field> fields = new ArrayList<>(Arrays.asList(item.getClass().getDeclaredFields()));
                Class<?> superClass = item.getClass().getSuperclass();
                while (superClass != null) {
                    fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
                    superClass = superClass.getSuperclass();
                }

                for (Field field : fields) {
                    field.setAccessible(true);
                    Object value = field.get(item);

                    // Create element for each field
                    Element fieldElement = doc.createElement(field.getName());
                    itemElement.appendChild(fieldElement);

                    if (field.getType().isPrimitive() || field.getType() == String.class) {
                        // Set text content for primitive types and String
                        fieldElement.setTextContent(String.valueOf(value));
                    } else if (value instanceof ListOfProduct) {
                        // Handle ListOfProduct attribute
                        ListOfProduct listOfProduct = (ListOfProduct) value;
                        for (Product product : listOfProduct.getProductList()) {
                            Element productElement = doc.createElement("product");
                            fieldElement.appendChild(productElement);

                            for (Field productField : product.getClass().getDeclaredFields()) {
                                productField.setAccessible(true);
                                Object productValue = productField.get(product);

                                Element productFieldElement = doc.createElement(productField.getName());
                                productFieldElement.setTextContent(String.valueOf(productValue));
                                productElement.appendChild(productFieldElement);
                            }
                        }
                    } else if (value instanceof ArrayList<?>) {
                        // Handle ArrayList attribute
                        ArrayList<?> arrayList = (ArrayList<?>) value;
                        for (Object arrayItem : arrayList) {
                            Element arrayItemElement = doc.createElement(arrayItem.getClass().getSimpleName());
                            fieldElement.appendChild(arrayItemElement);

                            for (Field arrayItemField : arrayItem.getClass().getDeclaredFields()) {
                                arrayItemField.setAccessible(true);
                                Object arrayItemValue = arrayItemField.get(arrayItem);

                                if (arrayItemField.getType().isPrimitive()
                                        || arrayItemField.getType() == String.class) {
                                    // Set text content for primitive types and String
                                    Element arrayItemFieldElement = doc.createElement(arrayItemField.getName());
                                    arrayItemFieldElement.setTextContent(String.valueOf(arrayItemValue));
                                    arrayItemElement.appendChild(arrayItemFieldElement);
                                } else if (arrayItemValue instanceof List<?>) {
                                    // Handle List attribute
                                    List<?> list = (List<?>) arrayItemValue;
                                    Element listElement = doc.createElement(arrayItemField.getName());
                                    arrayItemElement.appendChild(listElement);

                                    for (Object listItem : list) {
                                        Element listItemElement = doc
                                                .createElement(listItem.getClass().getSimpleName());
                                        listElement.appendChild(listItemElement);

                                        for (Field listItemField : listItem.getClass().getDeclaredFields()) {
                                            listItemField.setAccessible(true);
                                            Object listItemValue = listItemField.get(listItem);

                                            Element listItemFieldElement = doc.createElement(listItemField.getName());
                                            listItemFieldElement.setTextContent(String.valueOf(listItemValue));
                                            listItemElement.appendChild(listItemFieldElement);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Write XML to file
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(this.filename));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Reads data from an XML file and returns it as an object.
     * @param type The class type of the data to be read.
     * @return A data object read from the file.
     */
    public <T> T readData(Class<T> type) {
        T data = null;
    
        try {
            File file = new File(this.filename);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            // Parse XML
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
    
            // Get the first element with the tag name of the input type
            Element element = (Element) doc.getElementsByTagName(type.getSimpleName()).item(0);
            T item = type.getDeclaredConstructor().newInstance();
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
    
                if (field.getType().isPrimitive() || field.getType() == String.class) {
                    // Handle primitive types and String
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
                } else if (field.getType() == ArrayList.class || field.getType() == List.class) {
                    // Handle ArrayList and List types
                    NodeList list = element.getElementsByTagName(field.getName()).item(0).getChildNodes();
                    ParameterizedType listType = (ParameterizedType) field.getGenericType();
                    Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];
                    List<Object> objects = new ArrayList<>();
                    for (int j = 0; j < list.getLength(); j++) {
                        Node node = list.item(j);
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            Element elem = (Element) node;
                            Object obj = listClass.getDeclaredConstructor().newInstance();
                            for (Field f : listClass.getDeclaredFields()) {
                                f.setAccessible(true);
                                if (f.getType().isPrimitive() || f.getType() == String.class) {
                                    // Handle primitive types and String
                                    String value = elem.getElementsByTagName(f.getName()).item(0).getTextContent();
                                    // Set the value of the field based on its type
                                    if (f.getType() == float.class) {
                                        f.set(obj, Float.parseFloat(value));
                                    } else if (f.getType() == int.class) {
                                        f.set(obj, Integer.parseInt(value));
                                    } else if (f.getType() == double.class) {
                                        f.set(obj, Double.parseDouble(value));
                                    } else if (f.getType() == boolean.class) {
                                        f.set(obj, Boolean.parseBoolean(value));
                                    } else {
                                        f.set(obj, value);
                                    }
                                }
                            }
                            objects.add(obj);
                        }
                    }
                    field.set(item, objects);
                }
            }
            data = item;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * @brief Writes a single data object to an XML file.
     * @param data The data object to be written.
     */
    public <T> void writeData(T data) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            // Create a new XML document
            Document doc = db.newDocument();
    
            // Create the root element
            Element rootElement = doc.createElement(data.getClass().getSimpleName());
            doc.appendChild(rootElement);
    
            // Write the object data to the XML document
            writeObject(doc, rootElement, data);
    
            // Write the XML document to a file
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(this.filename));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void writeObject(Document doc, Element element, Object obj) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                if (value != null) {
                    if (field.getType().isPrimitive() || field.getType() == String.class) {
                        // Handle primitive types and String
                        Element fieldElement = doc.createElement(field.getName());
                        fieldElement.appendChild(doc.createTextNode(value.toString()));
                        element.appendChild(fieldElement);
                    } else if (field.getType() == ArrayList.class || field.getType() == List.class) {
                        // Handle ArrayList and List types
                        Element fieldElement = doc.createElement(field.getName());
                        List<?> list = (List<?>) value;
                        for (Object item : list) {
                            Element itemElement = doc.createElement(item.getClass().getSimpleName());
                            writeObject(doc, itemElement, item);
                            fieldElement.appendChild(itemElement);
                        }
                        element.appendChild(fieldElement);
                    } else {
                        // Handle custom classes
                        Element fieldElement = doc.createElement(field.getName());
                        writeObject(doc, fieldElement, value);
                        element.appendChild(fieldElement);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
