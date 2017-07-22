package io.falcon.assessment.repository.mock;

import com.google.common.collect.Lists;
import io.falcon.assessment.repository.AccessLogRepository;
import io.falcon.assessment.model.AccessLog;

import java.util.List;

public class MockAccessLogRepository implements AccessLogRepository {

    private List<AccessLog> repository = Lists.newArrayList();

    @Override
    public int countAll() {
        return repository.size();
    }

    @Override
    public void save(AccessLog inputAccessLog) {
        if(inputAccessLog != null)
            repository.add(inputAccessLog);
    }
}
