package io.falcon.assessment.model;

import lombok.Data;
import org.joda.time.LocalDateTime;

@Data
public class AccessLog {
    private int seq;
    private String request;
    private String method;
    private String response;
    private String referrer;
    private String message;
    private LocalDateTime logDateTime;
    private LocalDateTime insertedAt;

    @Override
    public String toString() {
        return "Seq : " + seq
                + ", Request : " + request
                + ", Method : " + method
                + ", Response : " + response
                + ", Referrer : " + referrer
                + ", Message : " + message
                + ", LogDateTime : " + logDateTime
                + ", InsertedAt : " + insertedAt;
    }

}
