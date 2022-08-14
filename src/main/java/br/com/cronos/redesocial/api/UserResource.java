package br.com.cronos.redesocial.api;

import br.com.cronos.redesocial.api.dto.CreateUserRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

@Path("/api/v1/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @POST
    public Response createUser( CreateUserRequest userRequest) {
        return Response.ok(userRequest).build();
    }

    @GET
    public Response listAllUsers() {
      return Response.ok(Arrays.asList(new CreateUserRequest())).build();
    }
}
