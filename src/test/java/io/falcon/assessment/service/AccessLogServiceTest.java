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
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;

public class AccessLogServiceTest {

    private AccessLogService service;
    private String requestUrl;

    @Before
    public void setup() throws IOException {
        requestUrl = "http://localhost:8080/falcon";
        AccessLogRepository accessLogRepository = createAndInitializeMockRepository();
        this.service = new MockAccessLogService(accessLogRepository, new AccessLogOutputDtoGenerator());
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
        AccessLogOutputDTO actual = service.getAccessLogsByRequest(emptyRequest, offset, size, sortType, requestUrl);

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
        AccessLogOutputDTO actual = service.getAccessLogsByRequest(request, offset, size, sortType, requestUrl);

        //then
        Assert.assertThat(actual, is(notNullValue()));
        Assert.assertThat(actual.getCountOfAccessLogs(), is(size));
        Assert.assertThat(actual.getNextUrl(), is(notNullValue()));
        for (AccessLogDTO accessLog : actual.getAccessLogs())
            Assert.assertThat(accessLog.getRequest(), is(request));
    }

    @Test
    public void givenDatetimeAndPageParameter_whenGetAccessLogsByLogDateTime_thenReturnAccessLogsTest() {
        //given
        DateTime logDateTime = TestDataFactory.getLogDateTime(System.getProperty("user.dir") + "/src/test/resources/data/input/inputLogDatetimeString.txt");
        int offset = 0;
        int size = 1;
        SortType sortType = SortType.ASCENDING;

        //when
        AccessLogOutputDTO actual = service.getAccessLogsByLogDateTime(logDateTime.toDate(), offset, size, sortType, requestUrl);

        //then
        Assert.assertThat(actual, is(notNullValue()));
        Assert.assertThat(actual.getNextUrl(), is(notNullValue()));
        for (AccessLogDTO accessLog : actual.getAccessLogs())
            Assert.assertThat(isValid(logDateTime.toDate(), accessLog.getLogDateTime()), is(true));
    }

    @Test
    public void givenSeqAndPageParameter_whenGetAccessLogsBySeq_thenReturnAccessLogsTest() {
        //given
        int offset = 2;
        int size = 10;
        int startSeq = 4;

        //when
        AccessLogOutputDTO actual = service.getAccessLogsBySeq(startSeq, offset, size, requestUrl);

        //then
        Assert.assertThat(actual, is(notNullValue()));
        Assert.assertThat(actual.getNextUrl(), is(nullValue()));
    }

    @Test
    public void givenAccessLogDTO_whenSaveAccessLog_thenSaveAccessLogTest() throws IOException {
        //given
        AccessLogDTO inputAccessLog = TestDataFactory.getAccessLogDTO(System.getProperty("user.dir") + "/src/test/resources/data/input/inputForSaveTest.json");

        //when
        service.saveAccessLog(inputAccessLog);

        //then
        AccessLogOutputDTO actual = service.getAccessLogsByLogDateTime(inputAccessLog.getLogDateTime(), 0, 1, SortType.ASCENDING, requestUrl);
        Assert.assertThat(actual, is(notNullValue()));
        for (AccessLogDTO dto : actual.getAccessLogs()) {
            Assert.assertThat(dto.getRequest(), is(inputAccessLog.getRequest()));
            Assert.assertThat(dto.getResponse(), is(inputAccessLog.getResponse()));
            Assert.assertThat(dto.getMethod(), is(inputAccessLog.getMethod()));
            Assert.assertThat(dto.getMessage(), is(inputAccessLog.getMessage()));
            Assert.assertThat(dto.getLogDateTime(), is(inputAccessLog.getLogDateTime()));
        }
    }

    private boolean isValid(Date expected, Date actual) {
        return DateUtils.isSameDay(actual, expected)
                || actual.after(expected);
    }

}
