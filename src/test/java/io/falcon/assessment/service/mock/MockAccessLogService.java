package io.falcon.assessment.service.mock;

import io.falcon.assessment.component.AccessLogOutputDtoGenerator;
import io.falcon.assessment.repository.AccessLogRepository;
import io.falcon.assessment.service.AccessLogService;

public class MockAccessLogService extends AccessLogService {
    public MockAccessLogService(AccessLogRepository accessLogRepository,
                                AccessLogOutputDtoGenerator accessLogOutputDtoGenerator) {
        super(accessLogRepository, accessLogOutputDtoGenerator);
    }

}
