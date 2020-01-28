package study.huhao.demo.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import study.huhao.demo.domain.contexts.usercontext.user.User;
import study.huhao.demo.domain.contexts.usercontext.user.UserRepository;
import study.huhao.demo.domain.contexts.usercontext.user.UserService;

@Component
public class QueryUserUseCase {

  private UserService userService;

  @Autowired
  public QueryUserUseCase(UserRepository userRepository) {
    this.userService = new UserService(userRepository);
  }

  public User get(String id) {
    return userService.get(id);
  }
}
