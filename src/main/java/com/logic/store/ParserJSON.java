package com.logic.store;

import org.json.JSONArray;
import org.json.JSONObject;
import lombok.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.logic.feature.ListOfProduct;
import com.logic.store.interfaces.*;

@AllArgsConstructor
public class ParserJSON implements Parseable {
    String filename;

    /**
     * @brief Writes a list of data to a file in JSON format
     * @param data The list of data
     */
    public <T> void writeDatas(List<T> data) {
        JSONArray jsonArray = new JSONArray();

        // Convert to JSON
        for (T ele : data) {
            JSONObject json = new JSONObject(ele);
            jsonArray.put(json);
        }

        try {
            Files.write(Paths.get(this.filename), jsonArray.toString(4).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Reads data from a file in JSON format and returns it as a list.
     * @param classType The class type of the data.
     * @return A list of data.
     */
    public <T> List<T> readDatas(Class<T> classType) {
        List<T> data = new ArrayList<>();

        try {
            String content = new String(Files.readAllBytes(Paths.get(this.filename)));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                T item = readData(classType, json);
                data.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    private <T> T readData(Class<T> classType, JSONObject json) throws Exception {
        Constructor<T> constructor = classType.getDeclaredConstructor();
        T item = constructor.newInstance();

        for (Field field : classType.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            if (json.has(fieldName)) {
                Object val = json.get(fieldName);
                if (field.getType().isAssignableFrom(ListOfProduct.class)) {
                    JSONObject jsonObject = (JSONObject) val;
                    ListOfProduct listOfProduct = new ListOfProduct();
                    for (Field elementField : ListOfProduct.class.getDeclaredFields()) {
                        elementField.setAccessible(true);
                        Object elementVal = jsonObject.get(elementField.getName());
                        if (elementField.getType().isAssignableFrom(List.class)) {
                            Type genericType = elementField.getGenericType();
                            if (genericType instanceof ParameterizedType) {
                                ParameterizedType parameterizedType = (ParameterizedType) genericType;
                                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                                if (actualTypeArguments.length == 1
                                        && actualTypeArguments[0] instanceof Class) {
                                    Class<?> listElementType = (Class<?>) actualTypeArguments[0];
                                    JSONArray jsonArrays = (JSONArray) elementVal;
                                    List<Object> list = new ArrayList<>();
                                    for (int j = 0; j < jsonArrays.length(); j++) {
                                        JSONObject listElementJson = jsonArrays.getJSONObject(j);
                                        Object element = listElementType.getDeclaredConstructor().newInstance();
                                        for (Field listElementField : listElementType.getDeclaredFields()) {
                                            listElementField.setAccessible(true);
                                            Object listElementVal = listElementJson
                                                    .get(listElementField.getName());
                                            listElementField.set(element, listElementVal);
                                        }
                                        list.add(element);
                                    }
                                    elementVal = list;
                                }
                            }
                        }
                        elementField.set(listOfProduct, elementVal);
                    }
                    val = listOfProduct;
                } else if (field.getType().isAssignableFrom(ArrayList.class)) {
                    // Get the generic type of the ArrayList
                    ParameterizedType arrayListType = (ParameterizedType) field.getGenericType();
                    Class<?> arrayListElementType = (Class<?>) arrayListType.getActualTypeArguments()[0];

                    // Parse the JSON array into an ArrayList
                    JSONArray jsonArray = (JSONArray) val;
                    ArrayList<Object> arrayList = new ArrayList<>();
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(j);
                        Object element = readData(arrayListElementType, jsonObject);
                        arrayList.add(element);
                    }
                    val = arrayList;
                } else if (!field.getType().isPrimitive() && !field.getType().isAssignableFrom(String.class)) {
                    // Handle fields of custom class types
                    JSONObject jsonObject = (JSONObject) val;
                    Object object = readData(field.getType(), jsonObject);
                    val = object;
                }
                field.set(item, val);
            }
        }

        return item;

    }

    /**
     * @brief Writes a single data object to a file in JSON format
     * @param data The data object
     */
    public <T> void writeData(T data) {
        JSONObject json = new JSONObject(data);

        try {
            Files.write(Paths.get(this.filename), json.toString(4).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Reads data from a file in JSON format and returns it as an object.
     * @param classType The class type of the data.
     * @return A data object.
     */
    public <T> T readData(Class<T> classType) {
        T data = null;

        try {
            String content = new String(Files.readAllBytes(Paths.get(this.filename)));
            JSONObject json = new JSONObject(content);
            data = readData(classType, json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}
