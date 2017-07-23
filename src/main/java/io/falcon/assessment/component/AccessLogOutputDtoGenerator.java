package io.falcon.assessment.component;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import io.falcon.assessment.model.AccessLog;
import io.falcon.assessment.model.dto.AccessLogDTO;
import io.falcon.assessment.model.dto.AccessLogOutputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;

@Component
public class AccessLogOutputDtoGenerator {

    private final String serverHost;
    private final String serverPort;

    @Autowired
    public AccessLogOutputDtoGenerator(@Value("${server.host}") String serverHost,
                                       @Value("${server.port}") String serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public AccessLogOutputDTO generateWith(List<AccessLog> accessLogs, int expectedSize) {
        int nextKey = 0;
        if(accessLogs.size() > expectedSize) {
            nextKey = Iterables.getLast(accessLogs).getSeq();
            accessLogs.remove(expectedSize);
        }

        return new AccessLogOutputDTO(convertAccessLogsToDto(accessLogs), expectedSize, nextKey, serverHost, serverPort);
    }

    private List<AccessLogDTO> convertAccessLogsToDto(List<AccessLog> accessLogs) {
        return Lists.newArrayList(Collections2.transform(accessLogs, new Function<AccessLog, AccessLogDTO>() {
            @Nullable
            @Override
            public AccessLogDTO apply(@Nullable AccessLog accessLog) {
                AccessLogDTO dto = new AccessLogDTO();
                dto.setRequest(accessLog.getRequest());
                dto.setReferrer(accessLog.getReferrer());
                dto.setMessage(accessLog.getMessage());
                dto.setMethod(accessLog.getMethod());
                dto.setResponse(accessLog.getResponse());
                dto.setLogDateTime(accessLog.getLogDateTime().toDate());

                return dto;
            }
        }));
    }
}
