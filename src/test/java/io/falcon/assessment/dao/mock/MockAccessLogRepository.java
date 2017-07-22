package io.falcon.assessment.dao.mock;

import com.google.common.collect.Lists;
import io.falcon.assessment.dao.AccessLogRepository;

import java.util.List;

public class MockAccessLogRepository implements AccessLogRepository {

    private List repository = Lists.newArrayList();

}
