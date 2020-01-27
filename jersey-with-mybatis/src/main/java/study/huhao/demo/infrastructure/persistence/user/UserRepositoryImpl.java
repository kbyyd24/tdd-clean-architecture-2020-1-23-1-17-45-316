package study.huhao.demo.infrastructure.persistence.user;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import study.huhao.demo.domain.contexts.usercontext.user.User;
import study.huhao.demo.domain.contexts.usercontext.user.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {

  private UserMapper userMapper;

  @Autowired
  public UserRepositoryImpl(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  @Override
  public void save(User user) {
    userMapper.insert(UserPO.of(user));
  }

  @Override
  public Optional<User> findById(String id) {
    return Optional.empty();
  }
}
