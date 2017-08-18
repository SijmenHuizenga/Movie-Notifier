package it.sijmen.movienotifier.integrationtests;

import io.restassured.http.ContentType;

import java.util.Collections;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

class UserCreateReadUpdateDeleteTest {

    HashMap<String, Object> testuser = new HashMap<>();

    String NAME = "name", EMAIL = "email", PHONE = "phonenumber", PASS = "password", NOTIFI = "notifications";

    void run(){
        create();
    }

    void create(){
        testuser.put(NAME, "cinemaenthousia");
        testuser.put(EMAIL, "enthousiast@example.com");
        testuser.put(PHONE, "+31698765432");
        testuser.put(PASS, "abcd1234");
        testuser.put(NOTIFI, Collections.singletonList("FBM"));

        given().contentType(ContentType.JSON).body(testuser).
        when().put("/user").
        then()
                .body(NAME, equalTo(name()))
                .body(EMAIL, equalTo(email()))
                .body(PHONE, equalTo(phone()))
                .body(PASS, equalTo(pass()))
                .body(NOTIFI, equalTo(notifi()));
    }

    private String name(){
        return (String) testuser.get(NAME);
    }
    private String email(){
        return (String) testuser.get(EMAIL);
    }
    private String phone(){
        return (String) testuser.get(PHONE);
    }
    private String pass(){
        return (String) testuser.get(PASS);
    }
    private String notifi(){
        return (String) testuser.get(NOTIFI);
    }

}
