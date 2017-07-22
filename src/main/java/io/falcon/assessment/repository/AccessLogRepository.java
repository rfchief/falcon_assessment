package io.falcon.assessment.repository;

import io.falcon.assessment.model.AccessLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessLogRepository {

    int countAll();

    void save(@Param("accessLog") AccessLog inputAccessLog);
}
