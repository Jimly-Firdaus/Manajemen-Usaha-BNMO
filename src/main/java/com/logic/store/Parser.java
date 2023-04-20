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
public class Parser implements Parseable {
    String filename;
    
    // Write to JSON
    public <T> void writeData(List<T> data) {
        JSONArray jsonArray = new JSONArray();
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

    // Read from JSON
    public <T> List<T> readFromJSON(Class<T> classType) {
        List<T> data = new ArrayList<>();

        try {
            String content = new String(Files.readAllBytes(Paths.get(this.filename)));
            JSONArray jsonArray = new JSONArray(content);
            Constructor<T> constructor = classType.getDeclaredConstructor();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                T item = constructor.newInstance();
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
