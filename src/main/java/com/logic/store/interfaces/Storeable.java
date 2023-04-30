package com.logic.store.interfaces;

import java.util.List;

public interface Storeable {
    public <T> void storeData(List<T> data);
    public <T> List<T> getData(Class<T> classType);
}
