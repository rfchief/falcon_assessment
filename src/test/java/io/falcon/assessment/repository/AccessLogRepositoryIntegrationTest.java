package io.falcon.assessment.repository;

import io.falcon.assessment.model.AccessLog;
import io.falcon.assessment.util.TestDataFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest
public class AccessLogRepositoryIntegrationTest {

    @Autowired
    private AccessLogRepository accessLogRepository;
    private List<AccessLog> initialAccessLogs;

    @Before
    public void setup() throws IOException {
        String inputFilePrefix = System.getProperty("user.dir") + "/src/test/resources/data/";
        this.initialAccessLogs = TestDataFactory.getAccessLogs(inputFilePrefix + "input/inputForInitialize.json");
        accessLogRepository.insertAll(initialAccessLogs);
    }

    @After
    public void destroy() {
        accessLogRepository.removeAll();
    }

    @Test
    public void doNothingTest() {
        System.out.println("Context Loading is OK!!!");
    }

}
