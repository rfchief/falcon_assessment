package io.falcon.assessment.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import io.falcon.assessment.model.AccessLog;
import io.falcon.assessment.model.dto.AccessLogDTO;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TestDataFactory {

    private static ObjectMapper objectMapper = new ObjectMapper()
                                                .setDateFormat(new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.US));
    private static FileReader fileReader = new FileReader();

    public static AccessLog getAccessLog(String filePath) throws IOException {
        if(isValid(filePath)) {
            AccessLogDTO accessLogDto = getAccessLogDTO(filePath);
            return convertDtoToAccessLog(accessLogDto, 1);
        }

        return null;
    }

    public static AccessLogDTO getAccessLogDTO(String filePath) throws IOException {
        String jsonString = fileReader.getFromStringFile(filePath);
        return objectMapper.readValue(jsonString, AccessLogDTO.class);
    }

    private static AccessLog convertDtoToAccessLog(AccessLogDTO accessLogDto, int index) {
        AccessLog accessLog = new AccessLog();
        accessLog.setSeq(index);
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
            int index = 0;
            for (AccessLogDTO element : elements)
                accessLogs.add(convertDtoToAccessLog(element, ++index));

            return accessLogs;
        }

        return null;
    }

    private static boolean isValid(String jsonString) {
        return StringUtils.isNotEmpty(jsonString);
    }

    public static String getRequestString(String filePath) {
        return fileReader.getFromStringFile(filePath);
    }

    public static DateTime getLogDateTime(String filePath) {
        return DateTime.parse(fileReader.getFromStringFile(filePath), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
