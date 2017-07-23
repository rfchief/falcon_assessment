package io.falcon.assessment.service;

import io.falcon.assessment.repository.mock.MockAccessLogRepository;
import io.falcon.assessment.service.mock.MockAccessLogService;
import org.junit.Before;
import org.junit.Test;

public class AccessLogServiceTest {

    private AccessLogService service;

    @Before
    public void setup() {
        this.service = new MockAccessLogService(new MockAccessLogRepository());
    }

    @Test
    public void doNothingTest() {
        System.out.printf("Everything is OK!!!");
    }

}
