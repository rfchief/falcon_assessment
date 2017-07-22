package io.falcon.assessment.dao;

import io.falcon.assessment.dao.mock.MockAccessLogRepository;
import org.junit.Before;
import org.junit.Test;

public class AccessLogRepositoryTest {

    private AccessLogRepository repository;

    @Before
    public void initialize() {
        this.repository = new MockAccessLogRepository();
    }

    @Test
    public void doNothingTest() {
        System.out.println("Everything is OK!!!");
    }

    @Test
    public void givenAccessLogObject_whenSave_thenSavedObjectTest() {
        //given

        //when

        //then

    }

}
