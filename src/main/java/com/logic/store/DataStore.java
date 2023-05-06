package com.logic.store;

import lombok.*;

import com.logic.store.interfaces.*;
import java.util.List;

@Value
@AllArgsConstructor
public class DataStore implements Storeable {
    Parseable parser;

    public <T> void storeDatas(List<T> data) {
        parser.writeDatas(data);
    }

    public <T> List<T> getDatas(Class<T> classType) {
        return parser.readDatas(classType);
    }

    public <T> void storeData(T data) {
        parser.writeData(data);
    }

    public <T> T getData(Class<T> classType) {
        return parser.readData(classType);
    }
}