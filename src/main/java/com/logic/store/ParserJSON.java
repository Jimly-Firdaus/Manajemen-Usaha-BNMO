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

import com.logic.store.interfaces.*;

@AllArgsConstructor
public class ParserJSON implements Parseable {
    String filename;
    
    /**
     * @brief Writes a list of data to a file in JSON format
     * @param data The list of data
     */
    public <T> void writeData(List<T> data) {
        JSONArray jsonArray = new JSONArray();
        
        // Convert to JSON
        for (T ele : data) {
            JSONObject json = new JSONObject(ele);
            jsonArray.put(json);
        }
        
        try {
            Files.write(Paths.get(filename), jsonArray.toString(4).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Reads data from a file in JSON format and returns it as a list.
     * @param classType The class type of the data.
     * @return A list of data.
     */
    public <T> List<T> readData(Class<T> classType) {
        List<T> data = new ArrayList<>();

        try {
            String content = new String(Files.readAllBytes(Paths.get(this.filename)));
            JSONArray jsonArray = new JSONArray(content);
            // Parse form JSON to Object type classType
            Constructor<T> constructor = classType.getDeclaredConstructor();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                T item = constructor.newInstance();
                // Restore to List<T>
                for (Field field : classType.getDeclaredFields()) {
                    field.setAccessible(true);
                    Object val = json.get(field.getName());
                    field.set(item, val);
                }
                data.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
