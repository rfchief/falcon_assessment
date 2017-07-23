package io.falcon.assessment.service;

import io.falcon.assessment.component.AccessLogOutputDtoGenerator;
import io.falcon.assessment.enums.SortType;
import io.falcon.assessment.model.AccessLog;
import io.falcon.assessment.model.dto.AccessLogOutputDTO;
import io.falcon.assessment.repository.AccessLogRepository;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessLogService {

    private final AccessLogRepository accessLogRepository;
    private final AccessLogOutputDtoGenerator accessLogOutputDtoGenerator;

    @Autowired
    public AccessLogService(AccessLogRepository accessLogRepository,
                            AccessLogOutputDtoGenerator accessLogOutputDtoGenerator) {
        this.accessLogRepository = accessLogRepository;
        this.accessLogOutputDtoGenerator = accessLogOutputDtoGenerator;
    }

    public AccessLogOutputDTO getAccessLogsByRequest(String request,
                                                           int offset,
                                                           int size,
                                                           SortType sortType) {
        if(StringUtils.isEmpty(request))
            return new AccessLogOutputDTO();

        List<AccessLog> accessLogs = accessLogRepository.findAllByRequest(request, offset, size + 1, sortType);
        return accessLogOutputDtoGenerator.generateWith(accessLogs, size);
    }

    public AccessLogOutputDTO getAccessLogsByLogDateTime(DateTime logDateTime,
                                                         int offset,
                                                         int size,
                                                         SortType sortType) {

        List<AccessLog> accessLogs = accessLogRepository.findAllByDateAfterThan(logDateTime, offset, size + 1, sortType);
        return accessLogOutputDtoGenerator.generateWith(accessLogs, size);
    }

    public AccessLogOutputDTO getAccessLogsBySeq(int seq,
                                                 int offset,
                                                 int size) {

        List<AccessLog> accessLogs = accessLogRepository.findAllBySeqAfterThan(seq, offset, size + 1);
        return accessLogOutputDtoGenerator.generateWith(accessLogs, size);
    }
}
