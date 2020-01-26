package study.huhao.demo.infrastructure.persistence.user;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

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

}
