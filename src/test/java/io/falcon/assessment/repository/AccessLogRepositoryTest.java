package io.falcon.assessment.repository;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import io.falcon.assessment.enums.SortType;
import io.falcon.assessment.model.AccessLog;
import io.falcon.assessment.repository.mock.MockAccessLogRepository;
import io.falcon.assessment.util.TestDataFactory;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class AccessLogRepositoryTest {

    private AccessLogRepository repository;
    private String inputFilePrefix;
    private List<AccessLog> initialAccessLogs;

    @Before
    public void initialize() throws IOException {
        this.inputFilePrefix = System.getProperty("user.dir") + "/src/test/resources/data/";
        this.repository = new MockAccessLogRepository();
        this.initialAccessLogs = TestDataFactory.getAccessLogs(inputFilePrefix + "/input/input.json");

        repository.save(initialAccessLogs);
    }

    @Test
    public void doNothingTest() {
        System.out.println("Everything is OK!!!");
    }

    @Test
    public void givenAccessLogObject_whenSave_thenSavedObjectTest() throws IOException {
        //given
        AccessLog inputAccessLog = TestDataFactory.getAccessLog(inputFilePrefix + "input/inputForSaveTest.json");
        int beforeCounts = repository.countAll();

        //when
        repository.save(inputAccessLog);

        //then
        Assert.assertThat(repository.countAll(), is(beforeCounts + 1));
    }

    @Test
    public void givenListOfAccessLog_whenSaveAll_thenSaveAllObjectTest() throws IOException {
        //given
        List<AccessLog> accessLogs = TestDataFactory.getAccessLogs(inputFilePrefix + "/input/input.json");
        int beforeCounts = repository.countAll();

        //when
        repository.save(accessLogs);

        //then
        Assert.assertThat(repository.countAll(), is(beforeCounts + accessLogs.size()));
    }

    @Test
    public void givenOffsetAndLimitAndSort_whenFindAll_thenReturnAccessLogsTest() {
        //given
        int offset = 0;
        int limit = 1;
        SortType sort = SortType.ASCENDING;
        AccessLog expected = initialAccessLogs.get(0);

        //when
        List<AccessLog> actual = repository.findAll(offset, limit, sort);

        //then
        Assert.assertThat(actual, is(notNullValue()));
        Assert.assertThat(actual.size(), is(limit));
        Assert.assertThat(actual.get(0), is(expected));
    }

    @Test
    public void givenOffsetAndLimitAndSortAndEmptyRequest_whenFindAllByRequest_thenReturnEmptyListTest() {
        //given
        int offset = 0;
        int limit = 10;
        String emptyRequest = StringUtils.EMPTY;

        //when
        List<AccessLog> actual = repository.findAllByRequest(emptyRequest, offset, limit, SortType.ASCENDING);

        //then
        Assert.assertThat(actual, is(notNullValue()));
        Assert.assertThat(actual.size(), is(0));
    }

    @Test
    public void givenOffsetAndLimitAndSortAndRequest_whenFindAllByRequest_thenReturnEmptyListTest() {
        //given
        int offset = 0;
        int limit = 10;
        String request = "/posts/posts/explore";

        //when
        List<AccessLog> actual = repository.findAllByRequest(request, offset, limit, SortType.ASCENDING);

        //then
        List<AccessLog> expected = getExpectedListBy(new Predicate<AccessLog>() {
                                                            @Override
                                                            public boolean apply(@Nullable AccessLog accessLog) {
                                                                return StringUtils.equals(accessLog.getRequest(), request);
                                                            }
                                                        });
        Assert.assertThat(actual, is(notNullValue()));
        Assert.assertThat(actual.size(), is(expected.size()));
        for (int i = 0; i < actual.size(); i++)
            Assert.assertThat(actual.get(i), is(expected.get(i)));
    }

    private List<AccessLog> getExpectedListBy(Predicate<AccessLog> predicate) {
        return Lists.newArrayList(Collections2.filter(initialAccessLogs, predicate));
    }
}
