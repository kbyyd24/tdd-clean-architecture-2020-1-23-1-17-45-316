package study.huhao.demo.adapters.restapi.resources.user;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.google.common.collect.ImmutableMap;
import io.restassured.http.ContentType;
import java.util.UUID;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import study.huhao.demo.adapters.restapi.resources.ResourceTest;

@DisplayName("/user")
class UserResourceTest extends ResourceTest {

  @Nested
  @DisplayName("POST /user")
  class createUser {

    @Test
    void should_create_user_successfully() {
      String username = "james_blunt";
      String displayName = "James Blunt";
      String signature = "You Are Beautiful";
      String email = "jblunt@sing.uk";
      given()
          .contentType(ContentType.JSON)
          .body(ImmutableMap.of("userName", username,
              "displayName", displayName,
              "signature", signature,
              "email", email))
          .when()
          .post("/user")
          .then()
          .statusCode(HttpStatus.CREATED.value())
          .contentType(ContentType.JSON)
          .body("id", notNullValue())
          .body("userName", is(username))
          .body("displayName", is(displayName))
          .body("signature", is(signature))
          .body("email", is(email));

    }
  }

  @Nested
  class editUser {

    @Test
    void should_edit_user_success() {
      String id = given()
          .contentType(ContentType.JSON)
          .body(ImmutableMap.of(
              "userName", "username",
              "displayName", "displayName",
              "signature", "signature",
              "email", "email"
          ))
          .when()
          .post("/user")
          .path("id");
      String editedUsername = "kobe_bryant";
      String editedDisplayName = "Kobe Bryant";
      String editedSignature = "Mamba out";
      String editedEmail = "kbryant@nba.com";

      given()
          .contentType(ContentType.JSON)
          .body(ImmutableMap.of(
              "userName", editedUsername,
              "displayName", editedDisplayName,
              "signature", editedSignature,
              "email", editedEmail
          ))
          .when()
          .put("/user/{id}", ImmutableMap.of("id", id))
          .then()
          .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void should_return_not_found_when_edit_not_exist_user() {
      String id = UUID.randomUUID().toString();
      given()
          .contentType(ContentType.JSON)
          .body(ImmutableMap.of(
              "userName", "username",
              "displayName", "displayName",
              "signature", "signature",
              "email", "email"
          ))
          .when()
          .put("/user/{id}", ImmutableMap.of("id", id))
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .body("message", equalTo("cannot find the user with id " + id));
    }
  }

  @Nested
  class getUser {

    @Test
    void should_get_user_success() {
      String username = "kobe_bryant";
      String displayName = "Kobe BryantName";
      String signature = "Mamba out";
      String email = "kbryant@nba.com";
      String id = given()
          .contentType(ContentType.JSON)
          .body(ImmutableMap.of(
              "userName", username,
              "displayName", displayName,
              "signature", signature,
              "email", email
          ))
          .when()
          .post("/user")
          .path("id");

      given()
          .when()
          .get("/user/{id}", ImmutableMap.of("id", id))
          .then()
          .statusCode(HttpStatus.OK.value())
          .body("userName", is(username))
          .body("displayName", is(displayName))
          .body("signature", is(signature))
          .body("email", is(email));
    }

    @Test
    void should_return_not_found_when_get_not_exist_user() {
      String id = UUID.randomUUID().toString();
      given()
          .when()
          .get("/user/{id}", ImmutableMap.of("id", id))
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .body("message", equalTo("cannot find the user with id " + id));
    }
  }

  @Nested
  class deleteUser {

    @Test
    void should_delete_user_success() {
      String id = given()
          .contentType(ContentType.JSON)
          .body(ImmutableMap.of(
              "userName", "kobe_bryant",
              "displayName", "Kobe BryantName",
              "signature", "Mamba out",
              "email", "kbryant@nba.com"
          ))
          .when()
          .post("/user")
          .path("id");

      given()
          .when()
          .delete("/user/{id}", ImmutableMap.of("id", id))
          .then()
          .statusCode(HttpStatus.NO_CONTENT.value());

      given()
          .when()
          .get("/user/{id}", ImmutableMap.of("id", id))
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void should_return_not_found_when_delete_not_exist_user() {
      String id = UUID.randomUUID().toString();
      given()
          .when()
          .delete("/users/{id}", ImmutableMap.of("id", id))
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .body("message", equalTo("cannot find the user with id " + id));
    }
  }

  @Nested
  class allUser {

    @Test
    void should_get_user_with_pagination() {
      IntStream.range(0, 5)
          .mapToObj(idx -> ImmutableMap.of(
              "userName", "kobe_bryant" + idx,
              "displayName", "Kobe BryantName" + idx,
              "signature", "Mamba out" + idx,
              "email", "kbryant@nba.com" + idx
          ))
          .forEach(request ->
              given()
                  .contentType(ContentType.JSON)
                  .body(request)
                  .when()
                  .post("/user"));

      given()
          .param("limit", 3)
          .param("offset", 3)
          .when()
          .get("/user")
          .then()
          .statusCode(HttpStatus.OK.value())
          .contentType(ContentType.JSON)
          .body("results", hasSize(2))
          .body("limit", equalTo(3))
          .body("offset", equalTo(3))
          .body("total", equalTo(5));
    }

    @Test
    void should_get_empty_result_when_not_found() {
      given()
          .param("limit", 3)
          .param("offset", 4)
          .when()
          .get("/user")
          .then()
          .statusCode(HttpStatus.OK.value())
          .contentType(ContentType.JSON)
          .body("results", hasSize(0))
          .body("limit", equalTo(3))
          .body("offset", equalTo(4))
          .body("total", equalTo(0));
    }
  }
}