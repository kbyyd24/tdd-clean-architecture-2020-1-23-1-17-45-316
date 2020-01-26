package study.huhao.demo.adapters.restapi.resources.user;

import java.util.UUID;
import lombok.Value;
import study.huhao.demo.adapters.restapi.resources.ResponseDto;
import study.huhao.demo.domain.contexts.usercontext.user.User;

@Value
public class UserDto implements ResponseDto {

  private UUID id;
  private String userName;
  private String displayName;
  private String signature;
  private String email;

  static UserDto of(User user) {
    return new UserDto(user.getId(), user.getUserName(), user.getDisplayName(), user.getSignature(), user.getEmail());
  }

}
