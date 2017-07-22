package io.falcon.assessment.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import io.falcon.assessment.model.AccessLog;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TestDataFactory {

    private static ObjectMapper objectMapper = new ObjectMapper()
                                                .setDateFormat(new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.US));;
    private static FileReader fileReader = new FileReader();

    public static AccessLog getAccessLog(String filePath) throws IOException {
        String jsonString = fileReader.getFromStringFile(filePath);

        if(isValid(jsonString))
            return objectMapper.readValue(jsonString, AccessLog.class);

        return null;
    }

    public static List<AccessLog> getAccessLogs(String filePath) throws IOException {
        String jsonString = fileReader.getFromStringFile(filePath);

        if(isValid(jsonString))
            return Lists.newArrayList(objectMapper.readValue(jsonString, AccessLog[].class));

        return null;
    }

    private static boolean isValid(String jsonString) {
        return StringUtils.isNotEmpty(jsonString);
    }
}
