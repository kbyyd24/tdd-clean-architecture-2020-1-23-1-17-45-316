package study.huhao.demo.domain.contexts.usercontext.user;

import java.util.Optional;
import study.huhao.demo.domain.core.concepts.Repository;

public interface UserRepository extends Repository {

  void save(User user);

  Optional<User> findById(String id);

  void deleteById(String id);

  boolean existById(String id);
}
