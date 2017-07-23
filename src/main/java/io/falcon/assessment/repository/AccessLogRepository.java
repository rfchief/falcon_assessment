package io.falcon.assessment.repository;

import io.falcon.assessment.enums.SortType;
import io.falcon.assessment.model.AccessLog;
import org.apache.ibatis.annotations.Param;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessLogRepository {

    int countAll();

    Integer insert(@Param("accessLog") AccessLog accessLog);

    Integer insertAll(@Param("accessLogs") List<AccessLog> accessLogs);

    List<AccessLog> findAll(@Param("offset") int offset,
                            @Param("limit") int limit,
                            @Param("sort") SortType sort);

    List<AccessLog> findAllByRequest(@Param("request") String request,
                                     @Param("offset") int offset,
                                     @Param("limit") int limit,
                                     @Param("sort") SortType sort);

    List<AccessLog> findAllByDateAfterThan(@Param("date") DateTime date,
                                           @Param("offset") int offset,
                                           @Param("limit") int limit,
                                           @Param("sort") SortType sort);

    List<AccessLog> findAllBySeqAfterThan(@Param("seq") int seq,
                                          @Param("offset") int offset,
                                          @Param("limit") int limit);

    Integer deleteAll();


}
