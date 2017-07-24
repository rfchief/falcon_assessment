package io.falcon.assessment.service;

import com.google.common.collect.Lists;
import io.falcon.assessment.component.AccessLogOutputDtoGenerator;
import io.falcon.assessment.enums.SortType;
import io.falcon.assessment.model.AccessLog;
import io.falcon.assessment.model.dto.AccessLogDTO;
import io.falcon.assessment.model.dto.AccessLogOutputDTO;
import io.falcon.assessment.repository.AccessLogRepository;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
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
                                                     SortType sortType,
                                                     String requestUrl) {
        if(StringUtils.isEmpty(request))
            return new AccessLogOutputDTO();

        List<AccessLog> accessLogs = accessLogRepository.findAllByRequest(request, offset, size + 1, sortType);
        return accessLogOutputDtoGenerator.generateWith(accessLogs, size, requestUrl);
    }

    public AccessLogOutputDTO getAccessLogsByLogDateTime(Date logDateTime,
                                                         int offset,
                                                         int size,
                                                         SortType sortType,
                                                         String requestUrl) {
        List<AccessLog> accessLogs = accessLogRepository.findAllByDateAfterThan(new DateTime(logDateTime), offset, size + 1, sortType);
        return accessLogOutputDtoGenerator.generateWith(accessLogs, size, requestUrl);
    }

    public AccessLogOutputDTO getAccessLogsBySeq(int seq,
                                                 int offset,
                                                 int size,
                                                 String requestUrl) {
        List<AccessLog> accessLogs = accessLogRepository.findAllBySeqAfterThan(seq, calculateOffset(offset, size), size + 1);
        return accessLogOutputDtoGenerator.generateWith(accessLogs, size, requestUrl);
    }

    public void saveAccessLog(AccessLogDTO accessLogDTO) {
        AccessLog accessLog = convertDtoToAccessLog(accessLogDTO);

        if(accessLog.isValid())
            accessLogRepository.insert(accessLog);
    }

    public void saveAccessLogs(List<AccessLogDTO> accessLogDTOList) {
        List<AccessLog> accessLogs = convertDtoToAccessLogs(accessLogDTOList);

        if(!CollectionUtils.isEmpty(accessLogs))
            accessLogRepository.insertAll(accessLogs);
    }

    public void removeAll() {
        accessLogRepository.deleteAll();
    }

    private List<AccessLog> convertDtoToAccessLogs(List<AccessLogDTO> accessLogDTOList) {
        List<AccessLog> accessLogs = Lists.newArrayList();
        for (AccessLogDTO accessLogDTO : accessLogDTOList) {
            AccessLog accessLog = convertDtoToAccessLog(accessLogDTO);
            if(!accessLog.isValid())
                throw new IllegalArgumentException();

            accessLogs.add(accessLog);
        }

        return accessLogs;
    }

    private AccessLog convertDtoToAccessLog(AccessLogDTO accessLogDTO) {
        AccessLog accessLog = new AccessLog();
        accessLog.setRequest(accessLogDTO.getRequest());
        accessLog.setReferrer(accessLogDTO.getReferrer());
        accessLog.setResponse(accessLogDTO.getResponse());
        accessLog.setMethod(accessLogDTO.getMethod());
        accessLog.setMessage(accessLogDTO.getMessage());
        accessLog.setLogDateTime(new DateTime(accessLogDTO.getLogDateTime()));

        return accessLog;
    }

    private int calculateOffset(int offset, int size) {
        return offset * size;
    }

}
