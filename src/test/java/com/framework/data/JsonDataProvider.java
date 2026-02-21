package com.framework.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.DataProvider;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonDataProvider {
    private static final ObjectMapper mapper = new ObjectMapper();

    @DataProvider(name = "userData")
    public Object[][] getUserData() throws IOException {
        List<Map<String, Object>> data = mapper.readValue(
                new File("src/test/resources/test_data/users.json"),
                new TypeReference<List<Map<String, Object>>>() {
                });

        Object[][] result = new Object[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            result[i][0] = data.get(i);
        }
        return result;
    }
}
