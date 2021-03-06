package study.huhao.demo.domain.contexts.usercontext.user;

import java.util.UUID;
import lombok.Getter;
import study.huhao.demo.domain.core.concepts.AggregateRoot;

@Getter
public class User implements AggregateRoot {

  private UUID id;
  private String userName;
  private String displayName;
  private String signature;
  private String email;

  public User(String userName, String displayName, String signature, String email) {
    this.id = UUID.randomUUID();
    this.userName = userName;
    this.displayName = displayName;
    this.signature = signature;
    this.email = email;
  }

  public User(UUID id, String userName, String displayName, String signature, String email) {
    this.id = id;
    this.userName = userName;
    this.displayName = displayName;
    this.signature = signature;
    this.email = email;
  }

  public void edit(String userName, String displayName, String signature, String email) {
    this.userName = userName;
    this.displayName = displayName;
    this.signature = signature;
    this.email = email;
  }
}
