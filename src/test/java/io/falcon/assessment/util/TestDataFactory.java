package io.falcon.assessment.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import io.falcon.assessment.model.AccessLog;
import io.falcon.assessment.model.dto.AccessLogDTO;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TestDataFactory {

    private static ObjectMapper objectMapper = new ObjectMapper()
                                                .setDateFormat(new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.US));
    private static FileReader fileReader = new FileReader();

    public static AccessLog getAccessLog(String filePath) throws IOException {
        String jsonString = fileReader.getFromStringFile(filePath);

        if(isValid(jsonString)) {
            AccessLogDTO accessLogDto = objectMapper.readValue(jsonString, AccessLogDTO.class);
            return convertDtoToAccessLog(accessLogDto);
        }

        return null;
    }

    private static AccessLog convertDtoToAccessLog(AccessLogDTO accessLogDto) {
        AccessLog accessLog = new AccessLog();
        accessLog.setRequest(accessLogDto.getRequest());
        accessLog.setResponse(accessLogDto.getResponse());
        accessLog.setMethod(accessLogDto.getMethod());
        accessLog.setReferrer(accessLogDto.getReferrer());
        accessLog.setMessage(accessLogDto.getMessage());
        accessLog.setLogDateTime(new DateTime(accessLogDto.getLogDateTime()));
        accessLog.setInsertedAt(new DateTime());

        return accessLog;
    }

    public static List<AccessLog> getAccessLogs(String filePath) throws IOException {
        String jsonString = fileReader.getFromStringFile(filePath);

        if(isValid(jsonString)) {
            AccessLogDTO[] elements = objectMapper.readValue(jsonString, AccessLogDTO[].class);
            List<AccessLog> accessLogs = Lists.newArrayList();
            for (AccessLogDTO element : elements)
                accessLogs.add(convertDtoToAccessLog(element));

            return accessLogs;
        }

        return null;
    }

    private static boolean isValid(String jsonString) {
        return StringUtils.isNotEmpty(jsonString);
    }
}
