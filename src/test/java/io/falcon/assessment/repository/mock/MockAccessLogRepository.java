package io.falcon.assessment.repository.mock;

import com.google.common.collect.Lists;
import io.falcon.assessment.model.AccessLog;
import io.falcon.assessment.repository.AccessLogRepository;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class MockAccessLogRepository implements AccessLogRepository {

    private List<AccessLog> repository = Lists.newArrayList();

    @Override
    public int countAll() {
        return repository.size();
    }

    @Override
    public Integer save(AccessLog inputAccessLog) {
        if(inputAccessLog != null)
            repository.add(inputAccessLog);

        return null;
    }

    @Override
    public Integer save(List<AccessLog> accessLogs) {
        if(CollectionUtils.isEmpty(accessLogs))
            return 0;

        repository.addAll(accessLogs);
        return null;
    }


}
