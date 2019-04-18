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

        UserIT user1 = new UserIT();

        user1.create();
        user1.read();
        user1.login();
        user1.update();
        user1.read();
        user1.updateOnlyUsername();
        user1.read();
        user1.login();

        UserIT user2 = new UserIT();
        user2.create();
        user2.login();


        WatcherIT watcher1 = new WatcherIT(user1);
        WatcherIT watcher2 = new WatcherIT(user2);
        WatcherIT watcher3 = new WatcherIT(user1);

        watcher1.create();
        watcher2.create();
        watcher3.create();

        watcher1.read();
        watcher2.read();

        watcher1.update();
        watcher2.update();

        watcher1.read();
        watcher2.read();
        watcher3.read();

        watcher1.delete();
        watcher2.delete();
        watcher3.delete();

        user1.delete();
    }

}
