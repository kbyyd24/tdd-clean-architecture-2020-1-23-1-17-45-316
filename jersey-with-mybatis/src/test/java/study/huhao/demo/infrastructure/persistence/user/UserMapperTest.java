package study.huhao.demo.infrastructure.persistence.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import study.huhao.demo.infrastructure.persistence.MapperTest;

public class UserMapperTest extends MapperTest {

  @Autowired
  private UserMapper userMapper;

  @Test
  void insert() {
    UserPO userPO = new UserPO(UUID.randomUUID().toString(), "username", "displayName", "signature", "email");

    userMapper.insert(userPO);

    assertThatExceptionOfType(DuplicateKeyException.class)
        .isThrownBy(() -> userMapper.insert(userPO));
  }

  @Test
  void should_update_success() {
    String id = UUID.randomUUID().toString();
    userMapper.insert(new UserPO(id, "username", "displayName", "signature", "email"));
    String username = "kobe_bryant";
    String displayName = "Kobe Bryant";
    String signature = "Mamba out";
    String email = "kbryant@nba.com";
    UserPO editedUser = new UserPO(id, username, displayName, signature, email);

    userMapper.update(editedUser);

    assertThat(userMapper.findById(id)).hasValueSatisfying(user -> {
      assertThat(user.getUserName()).isEqualTo(username);
      assertThat(user.getDisplayName()).isEqualTo(displayName);
      assertThat(user.getSignature()).isEqualTo(signature);
      assertThat(user.getEmail()).isEqualTo(email);
    });
  }

  @Test
  void should_find_by_id_success() {
    String username = "username";
    String displayName = "displayName";
    String signature = "signature";
    String email = "email";
    String id = UUID.randomUUID().toString();
    UserPO userPO = new UserPO(id, username, displayName, signature, email);
    userMapper.insert(userPO);

    Optional<UserPO> result = userMapper.findById(id);

    assertThat(result).hasValueSatisfying(user -> {
      assertThat(user.getId()).isEqualTo(id);
      assertThat(user.getUserName()).isEqualTo(username);
      assertThat(user.getDisplayName()).isEqualTo(displayName);
      assertThat(user.getSignature()).isEqualTo(signature);
      assertThat(user.getEmail()).isEqualTo(email);
    });
  }

  @Test
  void exist_by_id() {
    String id = UUID.randomUUID().toString();
    userMapper.insert(new UserPO(id, "username", "displayName", "signature", "email"));

    boolean exist = userMapper.existById(id);

    assertThat(exist).isTrue();
  }
}
