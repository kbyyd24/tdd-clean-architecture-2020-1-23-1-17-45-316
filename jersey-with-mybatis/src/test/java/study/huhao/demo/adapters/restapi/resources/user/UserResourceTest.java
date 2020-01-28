package study.huhao.demo.adapters.restapi.resources.user;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.google.common.collect.ImmutableMap;
import io.restassured.http.ContentType;
import java.util.UUID;
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

}