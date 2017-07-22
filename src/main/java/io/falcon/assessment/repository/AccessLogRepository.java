package io.falcon.assessment.repository;

import io.falcon.assessment.model.AccessLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessLogRepository {

    int countAll();

    Integer save(@Param("accessLog") AccessLog inputAccessLog);

    Integer save(@Param("accessLogs") List<AccessLog> accessLogs);
}
