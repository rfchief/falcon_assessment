package io.falcon.assessment.model.spring;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class AccessLogErrorResponse implements Serializable{
    private static final long serialVersionUID = 8105765388165905700L;

    private int status;
    private String error;
    private String path;
    private String timeStamp;

    public AccessLogErrorResponse(int status, Map<String, Object> errorAttributes) {
        this.status = status;
        this.error = (String) errorAttributes.get("error");
        this.path = (String) errorAttributes.get("path");
        this.timeStamp = errorAttributes.get("timestamp").toString();
    }
}
