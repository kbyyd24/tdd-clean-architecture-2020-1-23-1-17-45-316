package study.huhao.demo.domain.contexts.usercontext.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import study.huhao.demo.domain.core.common.excpetions.EntityNotFoundException;

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

  @Nested
  class editUser {

    @Test
    void should_edit_user_success() {
      String id = "userId";
      User mockUser = Mockito.mock(User.class);
      given(userRepository.findById(id)).willReturn(Optional.of(mockUser));
      String userName = "username";
      String displayName = "displayName";
      String signature = "signature";
      String email = "email";

      userService.edit(id, userName, displayName, signature, email);

      InOrder inOrder = inOrder(mockUser, userRepository);
      inOrder.verify(mockUser).edit(userName, displayName, signature, email);
      inOrder.verify(userRepository).save(mockUser);
    }

    @Test
    void should_throw_exception_when_user_not_found() {
      String id = UUID.randomUUID().toString();
      given(userRepository.findById(id)).willReturn(Optional.empty());

      assertThatExceptionOfType(EntityNotFoundException.class)
          .isThrownBy(() -> userService.edit(id, "username", "displayName", "signature", "email"));
    }
  }

  @Nested
  class getUser {

    @Test
    void should_get_user_success() {
      User mockUser = mock(User.class);
      String id = UUID.randomUUID().toString();
      given(userRepository.findById(id)).willReturn(Optional.of(mockUser));

      User user = userService.get(id);

      assertThat(user).isEqualTo(mockUser);
    }

    @Test
    void should_throw_exception_when_get_not_exist_user() {
      String id = UUID.randomUUID().toString();
      given(userRepository.findById(id)).willReturn(Optional.empty());

      assertThatExceptionOfType(EntityNotFoundException.class)
          .isThrownBy(() -> userService.get(id))
          .withMessage("cannot find the user with id " + id);
    }
  }

  @Nested
  class deleteUser{

    @Test
    void should_delete_user_success() {
      String id = UUID.randomUUID().toString();
      given(userRepository.existById(id)).willReturn(true);

      userService.delete(id);

      verify(userRepository).deleteById(id);
    }

    @Test
    void should_throw_exception_when_delete_not_exist_user() {
      String id = UUID.randomUUID().toString();
      given(userRepository.existById(id)).willReturn(false);

      assertThatExceptionOfType(EntityNotFoundException.class)
          .isThrownBy(() -> userService.delete(id))
          .withMessage("cannot find the user with id " + id);
    }
  }
}