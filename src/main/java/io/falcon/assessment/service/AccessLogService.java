package io.falcon.assessment.service;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import io.falcon.assessment.enums.SortType;
import io.falcon.assessment.model.AccessLog;
import io.falcon.assessment.model.dto.AccessLogDTO;
import io.falcon.assessment.model.dto.AccessLogOutputDTO;
import io.falcon.assessment.repository.AccessLogRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

@Service
public class AccessLogService {

    private final AccessLogRepository accessLogRepository;
    private final String serverHost;
    private final String serverPort;

    @Autowired
    public AccessLogService(AccessLogRepository accessLogRepository,
                            @Value("${local.server.host}") String serverHost,
                            @Value("${local.server.port}") String serverPort) {
        this.accessLogRepository = accessLogRepository;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public AccessLogOutputDTO getAccessLogsByRequest(String request,
                                                           int offset,
                                                           int size,
                                                           SortType sortType) {
        if(StringUtils.isEmpty(request))
            return new AccessLogOutputDTO();

        List<AccessLog> accessLogs = accessLogRepository.findAllByRequest(request, offset, size + 1, sortType);
        int nextKey = 0;
        if(accessLogs.size() > size) {
            nextKey = Iterables.getLast(accessLogs).getSeq();
            accessLogs.remove(size);
        }

        List<AccessLogDTO> accessDTOList = convertAccessLogsToDto(accessLogs);

        AccessLogOutputDTO accessLogOutputDTO = new AccessLogOutputDTO(accessDTOList, size, nextKey, serverHost, serverPort);
        return accessLogOutputDTO;
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
