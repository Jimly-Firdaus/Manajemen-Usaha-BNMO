import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import com.logic.store.DataStore;
import com.logic.store.interfaces.Parseable;

public class DataStoreTest {
    @Test
    public void testStoreAndGetData() {
        // Mock implementation of the Parseable interface with anonymous inner class
        Parseable mockParser = new Parseable() {
            List<String> data = new ArrayList<>();

            public <T> void writeData(List<T> data) {
                for (T item : data) {
                    this.data.add((String) item);
                }
            }

            public <T> List<T> readData(Class<T> classType) {
                return (List<T>) data;
            }
        };

        // DataStore instance with mockParser
        DataStore dataStore = new DataStore(mockParser);

        // Test the storeData
        List<String> testData = new ArrayList<>();
        testData.add("test1");
        testData.add("test2");
        dataStore.storeData(testData);

        // Test the getData
        List<String> resultData = dataStore.getData(String.class);
        assertEquals(testData, resultData);
    }
}
