package it.sijmen.movienotifier.integrationtests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import java.util.Collections;
import java.util.HashMap;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class UserIT {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserIT.class);

  private final HashMap<String, Object> testuser = new HashMap<>();

  private final String HEADKEY = "APIKEY";
  private final String NAME = "name",
      EMAIL = "email",
      PHONE = "phonenumber",
      PASS = "password",
      NOTIFI = "notifications",
      APIKEY = "apikey",
      ID = "id";

  void create() {
    testuser.put(NAME, "cinemaenthousia");
    testuser.put(EMAIL, "enthousiast@example.com");
    testuser.put(PHONE, "+31698765432");
    testuser.put(PASS, "abcd1234");
    testuser.put(NOTIFI, Collections.singletonList("FBM"));

    Response response = given().body(userdata()).when().put("/user").then().extract().response();
    testuser.put(APIKEY, response.path(APIKEY));
    testuser.put(ID, response.path(ID));
    checkUserResponse(response.then());
    LOGGER.info("Created User");
  }

  void read() {
    checkUserResponse(
        given().header(HEADKEY, apikey()).get("/user/" + id()).then().extract().response().then());
    LOGGER.info("Read User");
  }

  void update() {
    testuser.put(NAME, "someentousiast");
    testuser.put(EMAIL, "afdsf3a@coola.com");
    testuser.put(PHONE, "+31632165498");
    testuser.put(PASS, "1234ABCD");
    testuser.put(NOTIFI, Lists.newArrayList("FBM", "MIL"));
    checkUserResponse(
        given()
            .header(HEADKEY, apikey())
            .body(userdata())
            .post("/user/" + id())
            .then()
            .extract()
            .response()
            .then());
    LOGGER.info("Updated User");
  }

  void updateOnlyUsername() {
    HashMap<String, Object> body = new HashMap<>();
    body.put(NAME, "heeikbennieuw");
    testuser.put(NAME, "heeikbennieuw");
    checkUserResponse(
        given()
            .header(HEADKEY, apikey())
            .body(body)
            .post("/user/" + id())
            .then()
            .extract()
            .response()
            .then());
    LOGGER.info("Updated Username");
  }

  void delete() {
    given().header(HEADKEY, apikey()).delete("/user/" + id()).then().statusCode(200);
    LOGGER.info("Deleted User");
  }

  void login() {
    HashMap<String, Object> logindata = new HashMap<>();
    logindata.put(NAME, name());
    logindata.put(PASS, pass());
    checkUserResponse(given().body(logindata).post("/login/").then().extract().response().then());
    LOGGER.info("Login User");
  }

  private ValidatableResponse checkUserResponse(ValidatableResponse then) {
    return then.body("$", equalTo(responsedata()))
        .body("$", not(hasKey(PASS)))
        .body("$", not(hasKey("uuid")));
  }

  private String name() {
    return (String) testuser.get(NAME);
  }

  private String pass() {
    return (String) testuser.get(PASS);
  }

  String id() {
    return (String) testuser.get(ID);
  }

  String apikey() {
    return (String) testuser.get(APIKEY);
  }

  private HashMap<String, Object> userdata() {
    HashMap<String, Object> out = new HashMap<>(testuser);
    out.remove(APIKEY);
    out.remove(ID);
    return out;
  }

  private HashMap<String, Object> responsedata() {
    HashMap<String, Object> out = new HashMap<>(testuser);
    out.remove(PASS);
    return out;
  }
}
