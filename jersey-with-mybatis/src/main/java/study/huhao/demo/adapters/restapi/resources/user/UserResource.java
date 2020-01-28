package study.huhao.demo.adapters.restapi.resources.user;

import java.net.URI;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import study.huhao.demo.application.EditUserUseCase;
import study.huhao.demo.domain.contexts.usercontext.user.User;

@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class UserResource {

  private final EditUserUseCase editUserUseCase;

  @Autowired
  public UserResource(EditUserUseCase editUserUseCase) {
    this.editUserUseCase = editUserUseCase;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response create(UserCreateRequest request) {
    User user = editUserUseCase.create(request.getUserName(), request.getDisplayName(), request.getSignature(), request.getEmail());

    URI uri = UriBuilder.fromResource(this.getClass()).path("{id}").build(user.getId());
    return Response.created(uri).entity(UserDto.of(user)).build();
  }

  @Path("{id}")
  public UserSubResource userSubResource(@PathParam("id") String id) {
    return new UserSubResource(id, editUserUseCase);
  }
}
