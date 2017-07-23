package io.falcon.assessment.repository.mock;

import com.google.common.collect.Lists;
import io.falcon.assessment.enums.SortType;
import io.falcon.assessment.model.AccessLog;
import io.falcon.assessment.repository.AccessLogRepository;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class MockAccessLogRepository implements AccessLogRepository {

    private List<AccessLog> repository = Lists.newArrayList();

    @Override
    public int countAll() {
        return repository.size();
    }

    @Override
    public Integer insert(AccessLog inputAccessLog) {
        if(inputAccessLog != null)
            repository.add(inputAccessLog);

        return null;
    }

    @Override
    public Integer insertAll(List<AccessLog> accessLogs) {
        if(CollectionUtils.isEmpty(accessLogs))
            return 0;

        repository.addAll(accessLogs);
        return null;
    }

    @Override
    public List<AccessLog> findAll(int offset, int limit, SortType sort) {
        List<AccessLog> temp = getSubList(offset, sort);

        return temp.subList(0, temp.size() <= limit ? temp.size() : limit);
    }

    @Override
    public List<AccessLog> findAllByRequest(String referrer, int offset, int limit, SortType sort) {
        List<AccessLog> result = Lists.newArrayList();
        List<AccessLog> temp = getSubList(offset, sort);

        for(AccessLog accessLog : temp) {
            if(StringUtils.equals(accessLog.getRequest(), referrer))
                result.add(accessLog);

            if(result.size() == limit)
                return result;
        }

        return result;
    }

    @Override
    public List<AccessLog> findAllByDateAfterThan(DateTime date, int offset, int limit, SortType sort) {
        List<AccessLog> result = Lists.newArrayList();
        List<AccessLog> temp = getSubList(offset, sort);

        for(AccessLog accessLog : temp) {
            if(accessLog.getLogDateTime().isEqual(date)
                    || accessLog.getLogDateTime().isAfter(date))
                result.add(accessLog);

            if(result.size() == limit)
                return result;
        }

        return result;
    }

    @Override
    public Integer removeAll() {
        int size = repository.size();
        repository.clear();

        return size;
    }

    private List<AccessLog> getSubList(int offset, SortType sort) {
        List<AccessLog> temp = null;

        if(sort == SortType.DESCENDING) {
            temp = Lists.reverse(repository).subList(offset, repository.size());
        } else {
            temp = repository.subList(offset, repository.size());
        }
        return temp;
    }

}
