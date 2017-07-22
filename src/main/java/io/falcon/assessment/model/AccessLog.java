package io.falcon.assessment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessLog {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private int seq;
    private String request;
    @JsonProperty("verb")
    private String method;
    private String response;
    private String referrer;
    private String message;
    @JsonProperty("timestamp")
    private Date logDateTime;
    private Date insertedAt;

    public String getStrLogDateTime() {
        return dateFormat.format(logDateTime);
    }

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
