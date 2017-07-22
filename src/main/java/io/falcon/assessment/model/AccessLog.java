package io.falcon.assessment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessLog {

    private String request;
    private String method;
    private String response;
    private String referrer;
    private String message;
    @JsonProperty("timestamp")
    private Date logDateTime;

    @Override
    public String toString() {
        return "Request : " + request
                + ", Method : " + method
                + ", Response : " + response
                + ", Referrer : " + referrer
                + ", Message : " + message
                + ", LogDateTime : " + logDateTime;
    }

}
