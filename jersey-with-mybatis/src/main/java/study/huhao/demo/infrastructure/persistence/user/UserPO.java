package study.huhao.demo.infrastructure.persistence.user;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.huhao.demo.domain.contexts.usercontext.user.User;
import study.huhao.demo.infrastructure.persistence.PersistenceObject;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserPO implements PersistenceObject<User> {

  private String id;
  private String userName;
  private String displayName;
  private String signature;
  private String email;

  @Override
  public User toDomainModel() {
    return new User(UUID.fromString(id), userName, displayName, signature, email);
  }

  static UserPO of(User user) {
    return new UserPO(user.getId().toString(), user.getUserName(), user.getDisplayName(), user.getSignature(), user.getEmail());
  }
}
