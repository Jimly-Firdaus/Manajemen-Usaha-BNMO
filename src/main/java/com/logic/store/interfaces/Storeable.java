package com.logic.store.interfaces;

import java.util.List;

public interface Storeable {
    public <T> void storeDatas(List<T> data);
    public <T> List<T> getDatas(Class<T> classType);
    public <T> void storeData(T data);
    public <T> T getData(Class<T> classType);
}
