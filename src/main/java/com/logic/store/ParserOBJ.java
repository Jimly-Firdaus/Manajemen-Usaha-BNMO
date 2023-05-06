package com.logic.store;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import com.logic.store.interfaces.Parseable;

public class ParserOBJ implements Parseable {
    String filename;

    public ParserOBJ(String filename) {
        this.filename = filename;
    }

    public <T> void writeData(List<T> data) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.filename))) {
            out.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> List<T> readData(Class<T> classType) {
        List<T> data = new ArrayList<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(this.filename))) {
            data = (List<T>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }
}