package com.logic.store;

import lombok.*;

import com.logic.store.interfaces.*;
import java.util.List;

@Value
@AllArgsConstructor
public class DataStore implements Storeable {
    Parseable parser;

    public <T> void storeData(List<T> data) {
        parser.writeData(data);
    }

    public <T> List<T> getData(Class<T> classType) {
        return parser.readData(classType);
    }

}