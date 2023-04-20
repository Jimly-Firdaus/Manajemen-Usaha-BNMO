package com.logic.store.interfaces;

public interface Parseable {
    /**
     * Parseable type is Bill, Product, User
    */
    public void convertFromJSON();
    public void convertToJSON();

}
