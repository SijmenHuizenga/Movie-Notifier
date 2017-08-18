package it.sijmen.movienotifier.integrationtests;

import io.restassured.RestAssured;

class RunIntegrationTests {

    public static void main(String[] args) {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        new UserCreateReadUpdateDeleteTest().run();
    }

}
