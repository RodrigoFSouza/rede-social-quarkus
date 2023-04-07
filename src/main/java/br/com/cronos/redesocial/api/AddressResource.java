package br.com.cronos.redesocial.api;

import br.com.cronos.redesocial.api.dto.CreateAddressRequest;
import br.com.cronos.redesocial.domain.model.Address;
import br.com.cronos.redesocial.domain.model.User;
import io.quarkus.panache.common.Sort;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/api/v1/users/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AddressResource {
    @GET
    @Path("{userId}/address")
    public Response listAllAdrress(@PathParam("userId") Long userId) {
        Optional<User> userOp = User.findByIdOptional(userId);
        if (userOp.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        List<Address> results = Address.find("user", Sort.by("id"), userOp.get()).list();
        return Response.ok(results).build();
    }

    @GET
    @Path("/address")
    public Response listAllAdrress() {
        List<Address> results = Address.listAll();
        return Response.ok(results).build();
    }

    @POST
    @Path("{userId}/address")
    @Transactional
    public Response createAddress(@PathParam("userId") Long userId, CreateAddressRequest request) {
        Optional<User> userOp = User.findByIdOptional(userId);
        if (userOp.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        var address = new Address();
        address.setStreet(request.getStreet());
        address.setNumber(request.getNumber());
        address.setCity(request.getCity());
        address.setDistrict(request.getDistrict());
        address.setState(request.getState());
        address.setZipcode(request.getZipcode());
        address.setUser(userOp.get());
        Address.persist(address);

        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{userId}/address/{id}")
    @Transactional
    public Response updateAddress(@PathParam("userId") Long userId, @PathParam("id") Long id, Address address) {
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

        addressUpdate.setCity(address.getCity());
        addressUpdate.setState(address.getState());
        addressUpdate.setStreet(address.getStreet());
        addressUpdate.setZipcode(address.getZipcode());
        addressUpdate.setNumber(address.getNumber());
        addressUpdate.setDistrict(address.getDistrict());

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
