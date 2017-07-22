package io.falcon.assessment.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Profile("dev")
@SpringBootTest
public class AccessLogRepositoryIntegrationTest {

    @Autowired
    private AccessLogRepository accessLogRepository;

    @Test
    public void doNothingTest() {
        System.out.println("Context Loading is OK!!!");
    }

}
