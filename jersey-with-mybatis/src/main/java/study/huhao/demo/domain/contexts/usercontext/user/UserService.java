package study.huhao.demo.domain.contexts.usercontext.user;

import java.util.UUID;
import study.huhao.demo.domain.core.common.excpetions.EntityNotFoundException;
import study.huhao.demo.domain.core.concepts.Service;

public class UserService implements Service {

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
        .orElseThrow(() -> new EntityNotFoundException(User.class, UUID.fromString(id)));
    user.edit(userName, displayName, signature, email);
    userRepository.save(user);
  }

  public User get(String id) {
    return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class, UUID.fromString(id)));
  }
}
