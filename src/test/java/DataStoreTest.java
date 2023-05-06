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
            List<Object> data = new ArrayList<>();

            public <T> void writeData(T data) {
                this.data.add(data);
            }

            public <T> T readData(Class<T> classType) {
                return classType.cast(data.get(0));
            }

            public <T> void writeDatas(List<T> data) {
                for (T item : data) {
                    this.data.add(item);
                }
            }

            public <T> List<T> readDatas(Class<T> classType) {
                return (List<T>) data;
            }

            public void setFilename(String s) {
            }
        };

        // DataStore instance with mockParser
        DataStore dataStore = new DataStore(mockParser);

        // Test the storeData
        List<String> testData = new ArrayList<>();
        testData.add("test1");
        testData.add("test2");
        for (String item : testData) {
            dataStore.storeData(item);
        }

        // Test the getData
        List<String> resultData = dataStore.getDatas(String.class);
        assertEquals(testData, resultData);
    }
}
