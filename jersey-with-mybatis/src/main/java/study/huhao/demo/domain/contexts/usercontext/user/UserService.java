package study.huhao.demo.domain.contexts.usercontext.user;

import java.util.UUID;
import java.util.function.Function;
import study.huhao.demo.domain.core.common.excpetions.EntityNotFoundException;
import study.huhao.demo.domain.core.concepts.Service;

public class UserService implements Service {

  private static final Function<String, EntityNotFoundException> NOT_FOUND_EXCEPTION = id -> new EntityNotFoundException(User.class, UUID.fromString(id));

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User create(String userName, String displayName, String signature, String email) {
    User user = new User(userName, displayName, signature, email);
    userRepository.save(user);
    return user;
  }

  public void edit(String id, String userName, String displayName, String signature, String email) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> NOT_FOUND_EXCEPTION.apply(id));
    user.edit(userName, displayName, signature, email);
    userRepository.save(user);
  }

  public User get(String id) {
    return userRepository.findById(id).orElseThrow(() -> NOT_FOUND_EXCEPTION.apply(id));
  }

  public void delete(String id) {
    if (!userRepository.existById(id)) {
      throw NOT_FOUND_EXCEPTION.apply(id);
    }
    userRepository.deleteById(id);
  }
}
