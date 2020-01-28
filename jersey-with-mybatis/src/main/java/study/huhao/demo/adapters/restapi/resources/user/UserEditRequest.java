package study.huhao.demo.adapters.restapi.resources.user;

import lombok.Value;
import study.huhao.demo.adapters.restapi.resources.RequestDto;

@Value
public class UserEditRequest implements RequestDto {

  private String userName;
  private String displayName;
  private String signature;
  private String email;

}
