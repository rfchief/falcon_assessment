package io.falcon.assessment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private Date timestamp;

}
