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

        WatcherIT watcher1 = new WatcherIT(user);
        WatcherIT watcher2 = new WatcherIT(user);
        WatcherIT watcher3 = new WatcherIT(user);

        watcher1.create();
        watcher2.create();
        watcher3.create();

        watcher1.read();
        watcher2.read();
        watcher3.read();

        watcher1.update();
        watcher2.update();
        watcher3.update();

        watcher1.read();
        watcher2.read();
        watcher3.read();

        watcher1.delete();
        watcher2.delete();
        watcher3.delete();

        user.delete();
    }

}
