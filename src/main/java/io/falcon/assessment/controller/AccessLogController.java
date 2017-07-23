package io.falcon.assessment.controller;

import io.falcon.assessment.enums.SortType;
import io.falcon.assessment.model.dto.AccessLogDTO;
import io.falcon.assessment.model.dto.AccessLogOutputDTO;
import io.falcon.assessment.service.AccessLogService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/falcon")
public class AccessLogController {

    private final AccessLogService accessLogService;
    private final HttpServletRequest httpServletRequest;
    private final int maxSize;

    @Autowired
    public AccessLogController(AccessLogService accessLogService,
                               HttpServletRequest httpServletRequest,
                               @Value("${page.max.size}") int maxSize) {
        this.accessLogService = accessLogService;
        this.httpServletRequest = httpServletRequest;
        this.maxSize = maxSize;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveAccessLog(@RequestBody AccessLogDTO accessLogDTO) {
        if(accessLogDTO == null)
            throw new IllegalArgumentException("Access log is not valid.");

        accessLogService.saveAccessLog(accessLogDTO);
        return "OK";
    }

    @RequestMapping(value = "/key/logdatetime", method = RequestMethod.GET)
    public AccessLogOutputDTO getAccessLogsByLogDate(@RequestParam(value = "logdttm") String strLogDateTime,
                                                     @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                     @RequestParam(value = "size", defaultValue = "0") int size) {
        if(StringUtils.isEmpty(strLogDateTime))
            throw new IllegalArgumentException("LogDateTime parameter is not valid.");

        DateTime logDateTime = parseStringToDateTime(strLogDateTime);
        return accessLogService.getAccessLogsByLogDateTime(logDateTime.toDate(), offset, checkAndGetPageSize(size), SortType.ASCENDING, getRequestUrl());
    }

    @RequestMapping(value = "/key/seq", method = RequestMethod.GET)
    public AccessLogOutputDTO getAccessLogsBySeq(@RequestParam(value = "seq", defaultValue = "1") int seq,
                                                 @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                 @RequestParam(value = "size", defaultValue = "0") int size) {
        return accessLogService.getAccessLogsBySeq(seq, offset, checkAndGetPageSize(size), getRequestUrl());
    }

    private String getRequestUrl() {
        return "http://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + httpServletRequest.getRequestURI();
    }

    private DateTime parseStringToDateTime(String strLogDateTime) {
        return DateTime.parse(strLogDateTime, DateTimeFormat.forPattern("yyyy-mm-dd"));
    }

    private int checkAndGetPageSize(int size) {
        return size > maxSize || size == 0 ? maxSize : size;
    }
}
