package io.falcon.assessment.model;

import lombok.Data;
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
