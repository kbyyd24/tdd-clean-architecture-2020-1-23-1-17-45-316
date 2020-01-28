package study.huhao.demo.adapters.restapi.resources.user;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import study.huhao.demo.application.EditUserUseCase;

@Produces(MediaType.APPLICATION_JSON)
public class UserSubResource {

  private final String id;
  private final EditUserUseCase editUserUseCase;

  public UserSubResource(String id, EditUserUseCase editUserUseCase) {
    this.id = id;
    this.editUserUseCase = editUserUseCase;
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public void edit(UserEditRequest request) {
    editUserUseCase.edit(id, request.getUserName(), request.getDisplayName(), request.getSignature(), request.getEmail());
  }
}
