package br.com.cronos.redesocial.api;

import br.com.cronos.redesocial.api.dto.AddressResponse;
import br.com.cronos.redesocial.api.dto.CreateAddressRequest;
import br.com.cronos.redesocial.api.dto.UpdateAddressRequest;
import br.com.cronos.redesocial.api.dto.mapper.AddressMapper;
import br.com.cronos.redesocial.domain.model.Address;
import br.com.cronos.redesocial.domain.model.User;
import br.com.cronos.redesocial.utils.ConstraintViolationResponse;
import io.quarkus.panache.common.Sort;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/api/v1/users/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Address", description = "Manager address from user")
public class AddressResource {

    @Inject
    AddressMapper addressMapper;

    @GET
    @Path("{userId}/address")
    @Operation(summary = "API to list all address for an existing user")
    @APIResponse(responseCode = "200", description = "Return 200 when a list of returned with success")
    public Response listAllAdrress(@PathParam("userId") Long userId) {
        Optional<User> userOp = User.findByIdOptional(userId);
        if (userOp.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        List<Address> results = Address.list("user", Sort.by("id"), userOp.get());
        List<AddressResponse> responseResult = results.stream()
                .map(a -> addressMapper.toResponse(a))
                .collect(Collectors.toList());
        return Response.ok(responseResult).build();
    }

    @POST
    @Path("{userId}/address")
    @Transactional
    @Operation(summary = "API to create a new address for an existing user")
    @APIResponse(responseCode = "201", description = "Return 201 when a address created with success")
    @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = ConstraintViolationResponse.class)))
    public Response createAddress(@PathParam("userId") Long userId, @Valid CreateAddressRequest request) {
        Optional<User> userOp = User.findByIdOptional(userId);
        if (userOp.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        Address address = addressMapper.toAddress(request);
        address.setUser(userOp.get());
        Address.persist(address);

        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{userId}/address/{id}")
    @Transactional
    @Operation(summary = "API to updated a address for an existing user")
    @APIResponse(responseCode = "204", description = "Return 204 when a address updated with success")
    public Response updateAddress(@PathParam("userId") Long userId, @PathParam("id") Long id, UpdateAddressRequest request) {
        Optional<Address> addressOp = Address.findByIdOptional(id);
        if (addressOp.isEmpty()) {
            throw new NotFoundException();
        }

        Optional<User> userOp = User.findByIdOptional(userId);
        if (userOp.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        var addressUpdate = addressOp.get();
        if (!userOp.get().getId().equals(addressUpdate.getId())) {
            throw new BadRequestException("User this reques is diferent of address persisted");
        }

        addressMapper.toAddress(request, addressUpdate);

        Address.persist(addressUpdate);

        return Response.noContent().build();
    }

    @DELETE
    @Path("{userId}/address/{id}")
    @Transactional
    @Operation(summary = "API to deleted a address for an existing user")
    @APIResponse(responseCode = "204", description = "Return 204 when a address is deleted with success")
    public Response deleteAddress(@PathParam("userId") Long userId, @PathParam("id") Long id) {
        Optional<User> userOp = User.findByIdOptional(id);
        if (userOp.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        Optional<Address> addressOp = Address.findByIdOptional(id);
        addressOp.ifPresentOrElse(Address::delete, () -> { throw new NotFoundException(); });

        return Response.noContent().build();
    }
}
