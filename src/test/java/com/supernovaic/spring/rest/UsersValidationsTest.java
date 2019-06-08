package com.supernovaic.spring.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supernovaic.spring.rest.dto.UserFeignClientResponse;
import com.xebialabs.restito.semantics.Action;
import com.xebialabs.restito.semantics.Condition;
import com.xebialabs.restito.server.StubServer;
import io.restassured.RestAssured;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static com.xebialabs.restito.builder.ensure.EnsureHttp.ensureHttp;
import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestTestDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersValidationsTest {

    protected static StubServer externalServiceServer;

    @Value("${server.servlet.context-path:#{null}}")
    protected String path;

    @LocalServerPort
    protected int port;

    @BeforeClass
    public synchronized static void start() {
        externalServiceServer = new StubServer(8000).run();
    }

    @AfterClass
    public static void stop() {
        externalServiceServer.stop();
    }

    @Before
    public void cleanBeforeTest() {
        RestAssured.port = port;
        externalServiceServer.clear();
    }

    @After
    public void afterTest() {
        ensureHttp(externalServiceServer).gotStubsCommitmentsDone();
    }

    @Test
    public void checkExistingUsername() throws JsonProcessingException {
        //1. Create the object for the expected response from the external service.
        String username = "14c4b06b824ec593239362517f538b29";
        UserFeignClientResponse externalServiceResponse = new UserFeignClientResponse("c4ca4238a0b923820dcc509a6f75849b",
                "Foo User",
                "Foo",
                new Date(),
                username);

        //2. Finish setting up restito
        ObjectMapper mapper = new ObjectMapper();
        whenHttp(externalServiceServer).match(Condition.get("/user/get/" + username))
                .then(
                        Action.stringContent(mapper.writeValueAsString(externalServiceResponse)),
                        Action.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE),
                        Action.status(HttpStatus.OK_200));

        // 3. Execute the API and validate the response.
        RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .get(path + "/users/exists/" + username)
                .then()
                .statusCode(HttpStatus.FOUND_302.getStatusCode());
    }
}
