package io.falcon.assessment.controller;

import io.falcon.assessment.model.ParameterMessage;
import io.falcon.assessment.model.dto.AccessLogOutputDTO;
import io.falcon.assessment.service.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AccessLogWebSocketController {

    private final AccessLogService accessLogService;
    private final HttpServletRequest httpServletRequest;
    private final int maxSize;
    @Autowired
    public AccessLogWebSocketController(AccessLogService accessLogService,
                                        HttpServletRequest httpServletRequest,
                                        @Value("${page.max.size}") int maxSize) {
        this.accessLogService = accessLogService;
        this.httpServletRequest = httpServletRequest;
        this.maxSize = maxSize;
    }

    @MessageMapping("/accesslog")
    @SendTo("/falcon/show/logs")
    public AccessLogOutputDTO getAccessLogsBySeq (@Payload ParameterMessage parameterMessage) {
        return accessLogService.getAccessLogsBySeq(parameterMessage.getSeq(),
                                            parameterMessage.getOffset(),
                                            checkAndGetPageSize(parameterMessage.getSize()),
                                            getRequestUrl());
    }

    @MessageExceptionHandler
    @SendToUser("/falcon/topic/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }

    private int checkAndGetPageSize(int size) {
        return size > maxSize || size == 0 ? maxSize : size;
    }

    private String getRequestUrl() {
        return "http://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + httpServletRequest.getRequestURI();
    }
}
