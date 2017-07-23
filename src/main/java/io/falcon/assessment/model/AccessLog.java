package io.falcon.assessment.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.sql.Timestamp;

@Data
public class AccessLog {
    private int seq;
    private String request;
    private String method;
    private String response;
    private String referrer;
    private String message;
    private DateTime logDateTime;
    private DateTime insertedAt;

    public boolean isValid() {
        if(isEmpty(response))
            return false;

        if(isEmpty(referrer))
            return false;

        if(isEmpty(message))
            return false;

        if(isEmpty(method))
            return false;

        if(logDateTime == null)
            return false;

        return true;
    }

    private boolean isEmpty(String str) {
        return StringUtils.isEmpty(str);
    }

    @Override
    public String toString() {
        return  "Request : " + request
                + ", Method : " + method
                + ", Response : " + response
                + ", Referrer : " + referrer
                + ", Message : " + message
                + ", LogDateTime : " + logDateTime;
    }
}
