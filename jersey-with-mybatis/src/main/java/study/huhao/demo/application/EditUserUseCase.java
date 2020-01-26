package study.huhao.demo.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import study.huhao.demo.domain.contexts.usercontext.user.User;
import study.huhao.demo.domain.contexts.usercontext.user.UserRepository;
import study.huhao.demo.domain.contexts.usercontext.user.UserService;

@Component
public class EditUserUseCase {

  private UserService userService;

  @Autowired
  public EditUserUseCase(UserRepository userRepository) {
    this.userService = new UserService(userRepository);
  }

  public User create(String userName, String displayName, String signature, String email) {
    return userService.create(userName, displayName, signature, email);
  }
}
