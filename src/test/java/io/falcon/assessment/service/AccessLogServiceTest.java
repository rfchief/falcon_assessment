package io.falcon.assessment.service;

import io.falcon.assessment.component.AccessLogOutputDtoGenerator;
import io.falcon.assessment.enums.SortType;
import io.falcon.assessment.model.dto.AccessLogDTO;
import io.falcon.assessment.model.dto.AccessLogOutputDTO;
import io.falcon.assessment.repository.AccessLogRepository;
import io.falcon.assessment.repository.mock.MockAccessLogRepository;
import io.falcon.assessment.service.mock.MockAccessLogService;
import io.falcon.assessment.util.TestDataFactory;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class AccessLogServiceTest {

    private AccessLogService service;

    @Before
    public void setup() throws IOException {
        AccessLogRepository accessLogRepository = createAndInitializeMockRepository();
        this.service = new MockAccessLogService(accessLogRepository, new AccessLogOutputDtoGenerator("localhost", "8080"));
    }

    private MockAccessLogRepository createAndInitializeMockRepository() throws IOException {
        MockAccessLogRepository accessLogRepository = new MockAccessLogRepository();
        accessLogRepository.insertAll(TestDataFactory.getAccessLogs(System.getProperty("user.dir") + "/src/test/resources/data/input/inputForInitialize.json"));
        return accessLogRepository;
    }

    @Test
    public void doNothingTest() {
        System.out.printf("Everything is OK!!!");
    }


    @Test
    public void givenEmptyRequestAndPageParameter_whenGetAccessLogsByRequest_thenReturnEmptyListTest() {
        //given
        String emptyRequest = StringUtils.EMPTY;
        int offset = 0;
        int size = 1;
        SortType sortType = SortType.ASCENDING;

        //when
        AccessLogOutputDTO actual = service.getAccessLogsByRequest(emptyRequest, offset, size, sortType);

        //then
        Assert.assertThat(actual, is(notNullValue()));
        Assert.assertThat(actual.getCountOfAccessLogs(), is(0));
    }

    @Test
    public void givenRequestAndPageParameter_whenGetAccessLogsByRequest_thenReturnAccessLogsTest() {
        //given
        String request = TestDataFactory.getRequestString(System.getProperty("user.dir") + "/src/test/resources/data/input/inputRequestString.txt");
        int offset = 0;
        int size = 1;
        SortType sortType = SortType.ASCENDING;

        //when
        AccessLogOutputDTO actual = service.getAccessLogsByRequest(request, offset, size, sortType);

        //then
        Assert.assertThat(actual, is(notNullValue()));
        Assert.assertThat(actual.getCountOfAccessLogs(), is(size));
        Assert.assertThat(actual.getNextUrl(), is(notNullValue()));
        for (AccessLogDTO accessLog : actual.getAccessLogs())
            Assert.assertThat(accessLog.getRequest(), is(request));
    }

}
