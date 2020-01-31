package study.huhao.demo.infrastructure.persistence.user;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import study.huhao.demo.domain.contexts.usercontext.user.User;
import study.huhao.demo.domain.contexts.usercontext.user.UserCriteria;
import study.huhao.demo.domain.contexts.usercontext.user.UserRepository;
import study.huhao.demo.domain.core.common.Page;

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

  @Override
  public Page<User> findAllWithPagination(UserCriteria criteria) {
    long total = userMapper.countTotalByCriteria(criteria);
    List<UserPO> userPOs = userMapper.selectAllByCriteria(criteria);
    List<User> users = userPOs.stream().map(UserPO::toDomainModel).collect(toList());

    return new Page<>(users, criteria.getLimit(), criteria.getOffset(), total);
  }
}
