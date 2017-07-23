package io.falcon.assessment.repository;

import com.google.common.collect.Lists;
import io.falcon.assessment.enums.SortType;
import io.falcon.assessment.model.AccessLog;
import io.falcon.assessment.util.TestDataFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

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

    @Test
    public void whenCountAll_thenReturnNumberOfRecordsTest() {
        //given
        int expectedCount = initialAccessLogs.size();

        //when
        int actual = accessLogRepository.countAll();

        //then
        Assert.assertThat(actual, is(expectedCount));
    }

    @Test
    public void givenOffsetAndLimitAndAscending_whenFindAll_thenReturnAccessLogsTest() {
        //given
        int offset = 0;
        int limit = 100;
        SortType sort = SortType.ASCENDING;

        //when
        List<AccessLog> actual = accessLogRepository.findAll(offset, limit, sort);

        //then
        Assert.assertThat(actual, is(notNullValue()));
        Assert.assertThat(actual.size(), is(initialAccessLogs.size()));
        for (int i = 0; i < actual.size(); i++)
            assertAccessLogBetween(initialAccessLogs.get(i), actual.get(i));
    }

    @Test
    public void givenOffsetAndLimitAndDescending_whenFindAll_thenReturnAccessLogsTest() {
        //given
        int offset = 0;
        int limit = 100;
        SortType sort = SortType.DESCENDING;
        List<AccessLog> expectedAccessLogs = Lists.reverse(initialAccessLogs);

        //when
        List<AccessLog> actual = accessLogRepository.findAll(offset, limit, sort);

        //then
        Assert.assertThat(actual, is(notNullValue()));
        Assert.assertThat(actual.size(), is(initialAccessLogs.size()));
        for (int i = 0; i < actual.size(); i++)
            assertAccessLogBetween(expectedAccessLogs.get(i), actual.get(i));
    }



    private void assertAccessLogBetween(AccessLog expectedLog, AccessLog actualLog) {
        Assert.assertThat(actualLog.getRequest(), is(expectedLog.getRequest()));
        Assert.assertThat(actualLog.getMessage(), is(expectedLog.getMessage()));
        Assert.assertThat(actualLog.getMethod(), is(expectedLog.getMethod()));
        Assert.assertThat(actualLog.getReferrer(), is(expectedLog.getReferrer()));
        Assert.assertThat(actualLog.getResponse(), is(expectedLog.getResponse()));
        Assert.assertThat(actualLog.getLogDateTime(), is(expectedLog.getLogDateTime()));
    }

}
