package com.logic.store;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import com.logic.store.interfaces.Parseable;

import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
public class ParserOBJ implements Parseable {
    String filename;

    public <T> void writeDatas(List<T> data) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.filename))) {
            out.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> List<T> readDatas(Class<T> classType) {
        List<T> data = new ArrayList<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(this.filename))) {
            data = (List<T>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    public <T> void writeData(T data) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.filename))) {
            out.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public <T> T readData(Class<T> classType) {
        T data = null;
    
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(this.filename))) {
            data = (T) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }
}