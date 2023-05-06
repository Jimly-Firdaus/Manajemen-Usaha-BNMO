package com.logic.store.interfaces;

import java.util.List;

public interface Parseable {
    /**
     * Parseable type is Bill, Product, User
     */
    public <T> List<T> readDatas(Class<T> classType);
    public <T> T readData(Class<T> classType);
    public <T> void writeDatas(List<T> data);
    public <T> void writeData(T data);
}
