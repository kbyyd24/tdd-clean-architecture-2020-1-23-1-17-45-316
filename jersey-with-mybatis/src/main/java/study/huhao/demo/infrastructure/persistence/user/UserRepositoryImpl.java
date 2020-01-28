package study.huhao.demo.infrastructure.persistence.user;

import java.util.Optional;
import java.util.function.Consumer;
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
    Consumer<User> saveAction =
        userMapper.existById(user.getId().toString())
            ? newUser -> userMapper.update(UserPO.of(newUser))
            : existUser -> userMapper.insert(UserPO.of(existUser));
    saveAction.accept(user);
  }

  @Override
  public Optional<User> findById(String id) {
    return userMapper.findById(id).map(UserPO::toDomainModel);
  }

  @Override
  public void deleteById(String id) {
    userMapper.delete(id);
  }

  @Override
  public boolean existById(String id) {
    return userMapper.existById(id);
  }
}
