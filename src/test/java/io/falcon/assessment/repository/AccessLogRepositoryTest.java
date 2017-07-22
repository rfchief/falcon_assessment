package io.falcon.assessment.repository;

import io.falcon.assessment.repository.mock.MockAccessLogRepository;
import io.falcon.assessment.model.AccessLog;
import io.falcon.assessment.util.TestDataFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;

public class AccessLogRepositoryTest {

    private AccessLogRepository repository;
    private String inputFilePrefix;

    @Before
    public void initialize() {
        this.inputFilePrefix = System.getProperty("user.dir") + "/src/test/resources/data/";
        this.repository = new MockAccessLogRepository();
    }

    @Test
    public void doNothingTest() {
        System.out.println("Everything is OK!!!");
    }

    @Test
    public void givenAccessLogObject_whenSave_thenSavedObjectTest() throws IOException {
        //given
        String inputFilePath = inputFilePrefix + "input/inputForSaveTest.json";
        AccessLog inputAccessLog = TestDataFactory.getAccessLog(inputFilePath);
        int beforeCounts = repository.countAll();

        //when
        repository.save(inputAccessLog);

        //then
        Assert.assertThat(repository.countAll(), is(beforeCounts + 1));
    }

}
