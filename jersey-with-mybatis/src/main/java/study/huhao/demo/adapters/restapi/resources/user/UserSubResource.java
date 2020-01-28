package study.huhao.demo.adapters.restapi.resources.user;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import study.huhao.demo.application.EditUserUseCase;
import study.huhao.demo.application.QueryUserUseCase;

@Produces(MediaType.APPLICATION_JSON)
public class UserSubResource {

  private final String id;
  private final EditUserUseCase editUserUseCase;
  private final QueryUserUseCase queryUserUseCase;

  public UserSubResource(String id, EditUserUseCase editUserUseCase, QueryUserUseCase queryUserUseCase) {
    this.id = id;
    this.editUserUseCase = editUserUseCase;
    this.queryUserUseCase = queryUserUseCase;
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public void edit(UserEditRequest request) {
    editUserUseCase.edit(id, request.getUserName(), request.getDisplayName(), request.getSignature(), request.getEmail());
  }

  @GET
  public UserDto get() {
    return UserDto.of(queryUserUseCase.get(id));
  }
}
