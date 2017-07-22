package io.falcon.assessment.repository;

import io.falcon.assessment.enums.SortType;
import io.falcon.assessment.model.AccessLog;
import io.falcon.assessment.repository.mock.MockAccessLogRepository;
import io.falcon.assessment.util.TestDataFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
}
