package study.huhao.demo.domain.contexts.usercontext.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;
  @InjectMocks
  private UserService userService;

  @Nested
  class createUser {

    @Test
    void should_create_successfully() {
      String userName = "userName";
      String displayName = "displayName";
      String signature = "signature";
      String email = "email";

      User createdUser = userService.create(userName, displayName, signature, email);

      verify(userRepository).save(createdUser);
      assertThat(createdUser.getId()).isNotNull();
    }
  }

}