package io.falcon.assessment.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;

@Data
public class AccessLogOutputDTO implements Serializable{
    private static final long serialVersionUID = -7408490711095498827L;

    private String nextUrl;
    private int size;
    private List<AccessLogDTO> accessLogs;

    public AccessLogOutputDTO() {
    }

    public AccessLogOutputDTO(List<AccessLogDTO> accessLogs,
                              int size,
                              int nextKey,
                              String requestUrl) {
        if(nextKey > 0)
            this.nextUrl = requestUrl + "?seq=" + nextKey + "&size=" + size;

        this.size = size;
        this.accessLogs = accessLogs;
    }

    @JsonIgnore
    public int getCountOfAccessLogs() {
        if(CollectionUtils.isEmpty(accessLogs))
            return 0;

        return accessLogs.size();
    }
}
