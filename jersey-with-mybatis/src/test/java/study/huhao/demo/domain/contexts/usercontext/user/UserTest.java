package study.huhao.demo.domain.contexts.usercontext.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class UserTest {

  @Nested
  class constructor {

    @Test
    void should_new_instance_correctly() {
      String userName = "userName";
      String displayName = "displayName";
      String signature = "signature";
      String email = "email";

      User user = new User(userName, displayName, signature, email);

      assertThat(user.getId()).isNotNull();
      assertThat(user.getUserName()).isEqualTo(userName);
      assertThat(user.getDisplayName()).isEqualTo(displayName);
      assertThat(user.getSignature()).isEqualTo(signature);
      assertThat(user.getEmail()).isEqualTo(email);
    }
  }
}