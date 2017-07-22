package io.falcon.assessment.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.falcon.assessment.model.AccessLog;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class TestDataFactory {

    private static ObjectMapper objectMapper = new ObjectMapper()
                                                .setDateFormat(new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.KOREAN));;
    private static FileReader fileReader = new FileReader();

    public static AccessLog getAccessLog(String filePath) throws IOException {
        String jsonString = fileReader.getFromStringFile(filePath);
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.US));

        if(StringUtils.isNotEmpty(jsonString))
            return objectMapper.readValue(jsonString, AccessLog.class);

        return null;
    }

}
