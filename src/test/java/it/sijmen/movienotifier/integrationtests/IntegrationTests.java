package it.sijmen.movienotifier.integrationtests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;

class IntegrationTests {

    public static void main(String[] args) {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();

        UserIT user = new UserIT();

        user.create();
        user.read();
        user.login();
        user.update();
        user.read();
        user.login();
        user.delete();
    }

}
