package io.falcon.assessment.service;

import io.falcon.assessment.model.dto.AccessLogDTO;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"server.port=0", "server.host=localhost"})
public class AccessLogServiceIntegrationTest {

    @Autowired
    private AccessLogService service;

    @Before
    public void setup() throws IOException {
        List<AccessLogDTO> initializeData = TestDataFactory.getAccessLogDTOList(System.getProperty("user.dir") + "/src/test/resources/data/input/inputForInitialize.json");
        service.saveAccessLogs(initializeData);
    }

    @After
    public void destroy() {
        service.removeAll();
    }

    @Test
    public void doNothingTest() {
        System.out.printf("Context Loading is OK!!!");
    }

}
