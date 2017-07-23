package io.falcon.assessment.repository;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import io.falcon.assessment.enums.SortType;
import io.falcon.assessment.model.AccessLog;
import io.falcon.assessment.util.TestDataFactory;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Nullable;
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
        accessLogRepository.deleteAll();
    }

    @Test
    public void doNothingTest() {
        System.out.println("Context Loading is OK!!!");
    }

    @Test
    public void givenListOfAccessLog_whenInsertAll_thenSaveAllObjectTest() throws IOException {
        //given
        AccessLog inputAccessLog = TestDataFactory.getAccessLog(System.getProperty("user.dir") + "/src/test/resources/data/input/inputForSaveTest.json");
        int beforeCounts = accessLogRepository.countAll();
        int expectedCounts = beforeCounts + 1;

        //when
        accessLogRepository.insert(inputAccessLog);

        //then
        Assert.assertThat(accessLogRepository.countAll(), is(expectedCounts));
        AccessLog actual = accessLogRepository.findAllBySeqAfterThan(expectedCounts, 0, 1).get(0);
        assertAccessLogBetween(inputAccessLog, actual);
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

    @Test
    public void givenOffsetAndLimitAndSortAndRequest_whenFindAllByRequest_thenReturnEmptyListTest() {
        //given
        int offset = 0;
        int limit = 1;
        String request = "/posts/posts/explore";

        //when
        List<AccessLog> actual = accessLogRepository.findAllByRequest(request, offset, limit, SortType.ASCENDING);

        //then
        List<AccessLog> expected = getExpectedListBy(request);
        Assert.assertThat(actual, is(notNullValue()));
        for (AccessLog accessLog : actual)
            assertAccessLogBetween(accessLog, expected.get(0));
    }

    @Test
    public void givenOffsetAndLimitAndSortAndFirstInsertedLogDate_whenFindAllByDateAfterThan_thenReturnAccessLogsTest() {
        //given
        int offset = 0;
        int limit = 5;
        SortType sort = SortType.ASCENDING;
        DateTime logDateTime = Iterables.getFirst(initialAccessLogs, new AccessLog()).getLogDateTime();

        //when
        List<AccessLog> actual = accessLogRepository.findAllByDateAfterThan(logDateTime, offset, limit, sort);

        //then
        Assert.assertThat(actual, is(notNullValue()));
        Assert.assertThat(actual.size() > 0, is(true));
        for (AccessLog accessLog : actual) {
            DateTime actualLogDateTime = accessLog.getLogDateTime();
            Assert.assertThat(isValidLogDateTime(logDateTime, actualLogDateTime), is(true));
        }
    }

    @Test
    public void givenOffsetAndLimitAndSortAndNow_whenFindAllByDateAfterThan_thenReturnEmptyListTest() {
        //given
        int offset = 0;
        int limit = 5;
        SortType sort = SortType.ASCENDING;

        //when
        List<AccessLog> actual = accessLogRepository.findAllByDateAfterThan(new DateTime(), offset, limit, sort);

        //then
        Assert.assertThat(actual, is(notNullValue()));
        Assert.assertThat(actual.size(), is(0));
    }

    @Test
    public void givenOffsetAndLimitAndSeq_whenFindAllBySeqAfterThan_thenReturnAccessLogsTest() {
        //given
        int offset = 0;
        int limit = 5;
        int givenSeq = 2;

        //when
        List<AccessLog> actual = accessLogRepository.findAllBySeqAfterThan(givenSeq, offset, limit);

        //then
        Assert.assertThat(actual, is(notNullValue()));
        Assert.assertThat(actual.size() > 0, is(true));
        for (AccessLog accessLog : actual)
            Assert.assertThat(accessLog.getSeq() >= givenSeq, is(true));
    }

    private boolean isValidLogDateTime(DateTime timestamp, DateTime actualTimestamp) {
        return actualTimestamp.equals(timestamp) || actualTimestamp.isAfter(timestamp);
    }

    private List<AccessLog> getExpectedListBy(String request) {
        Predicate<AccessLog> predicate = new Predicate<AccessLog>() {
            @Override
            public boolean apply(@Nullable AccessLog accessLog) {
                return StringUtils.equals(accessLog.getRequest(), request);
            }
        };
        return Lists.newArrayList(Collections2.filter(initialAccessLogs, predicate));
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
