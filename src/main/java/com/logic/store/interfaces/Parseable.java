package com.logic.store.interfaces;

import java.util.List;

public interface Parseable {
    /**
     * Parseable type is Bill, Product, User
    */
    public <T> List<T> readFromJSON(Class<T> classType);
    public <T> void writeData(List<T> data);

}
