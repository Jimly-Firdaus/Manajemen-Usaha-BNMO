package com;
import com.logic.store.Database;

public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        
        // create a new table
        String tableName = "testTable";
        String colConstraint = "id INT PRIMARY KEY, name VARCHAR(255)";
        db.createtable(tableName, colConstraint);
        
        // insert data into the table
        String tableCol = "id, name";
        String values = "1, 'Lombok'";
        db.insertData(tableName, tableCol, values);
        
        // update data in the table
        String setValues = "name = 'Lombok2'";
        String condition = "id = 1";
        db.updateData(tableName, setValues, condition);
        
        // delete data from the table
        condition = "id = 1";
        db.deleteData(tableName, condition);
    }
}
