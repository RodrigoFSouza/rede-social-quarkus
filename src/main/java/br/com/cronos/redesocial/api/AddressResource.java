package br.com.cronos.redesocial.api;

import br.com.cronos.redesocial.api.dto.AddressResponse;
import br.com.cronos.redesocial.api.dto.CreateAddressRequest;
import br.com.cronos.redesocial.api.dto.UpdateAddressRequest;
import br.com.cronos.redesocial.api.dto.mapper.AddressMapper;
import br.com.cronos.redesocial.domain.model.Address;
import br.com.cronos.redesocial.domain.model.User;
import io.quarkus.panache.common.Sort;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/api/v1/users/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AddressResource {

    @Inject
    AddressMapper addressMapper;

    @GET
    @Path("{userId}/address")
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
    public Response createAddress(@PathParam("userId") Long userId, CreateAddressRequest request) {
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
