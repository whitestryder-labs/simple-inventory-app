package org.whitestryder.labs.api.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.whitestryder.labs.support.MockWebSecurityTestConfig;


/**
 * Provide an integration test for the REST API /health resource
 * @author steve
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(MockWebSecurityTestConfig.class)
public class HealthControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * It should report health.
     */
    @Test
    public void itShouldReportHealth() {
        ResponseEntity<String> responseEntity =
        	restTemplate.getForEntity("/health", String.class);
        
        String responseBody = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("healthy", responseBody);
    }
}
