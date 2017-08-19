package it.sijmen.movienotifier.integrationtests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import jersey.repackaged.com.google.common.collect.Lists;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class UserIT {

    private final HashMap<String, Object> testuser = new HashMap<>();

    private final String HEADKEY = "APIKEY";
    private final String NAME = "name", EMAIL = "email", PHONE = "phonenumber", PASS = "password", NOTIFI = "notifications", APIKEY = "apikey", ID = "id";

    void create(){
        testuser.put(NAME, "cinemaenthousia");
        testuser.put(EMAIL, "enthousiast@example.com");
        testuser.put(PHONE, "+31698765432");
        testuser.put(PASS, "abcd1234");
        testuser.put(NOTIFI, Collections.singletonList("FBM"));

        Response response = given().contentType(ContentType.JSON).body(userdata()).when().put("/user").then().extract().response();
        testuser.put(APIKEY, response.path(APIKEY));
        testuser.put(ID, response.path(ID));
        checkUserResponse(response.then());

    }

    void read(){
        checkUserResponse(
                given().header(HEADKEY, apikey()).get("/user/"+id()).then().extract().response().then()
        );
    }

    void update(){
        testuser.put(NAME, "someentousiast");
        testuser.put(EMAIL, "afdsf3a@coola.com");
        testuser.put(PHONE, "+31632165498");
        testuser.put(PASS, "1234ABCD");
        testuser.put(NOTIFI, Lists.newArrayList("FBM", "MIL"));
        checkUserResponse(
                given().header(HEADKEY, apikey()).body(userdata()).post("/user/"+id()).then().extract().response().then()
        );
    }

    void delete(){
        given().header(HEADKEY, apikey()).delete("/user/"+id()).then().statusCode(200);
    }

    void login(){
        HashMap<String, Object> logindata = new HashMap<>();
        logindata.put(NAME, name());
        logindata.put(PASS, pass());
        checkUserResponse(
                given().body(logindata).post("/login/").then().extract().response().then()
        );
    }

    private ValidatableResponse checkUserResponse(ValidatableResponse then) {
        return then.body(NAME, equalTo(name()))
            .body(EMAIL, equalTo(email()))
            .body(PHONE, equalTo(phone()))
            .body(NOTIFI, equalTo(notifi()))
            .body(ID, equalTo(id()))
            .body(APIKEY, equalTo(apikey()))
            .body("$", not(hasKey(PASS)));
    }

    String name(){
        return (String) testuser.get(NAME);
    }
    String email(){
        return (String) testuser.get(EMAIL);
    }
    String phone(){
        return (String) testuser.get(PHONE);
    }
    String pass(){
        return (String) testuser.get(PASS);
    }
    List<String> notifi(){
        return (List<String>) testuser.get(NOTIFI);
    }
    String id(){
        return (String) testuser.get(ID);
    }
    String apikey(){
        return (String) testuser.get(APIKEY);
    }
    HashMap<String, Object> userdata(){
        HashMap<String, Object> out = new HashMap<>(testuser);
        out.remove(APIKEY);
        out.remove(ID);
        return out;
    }
}
