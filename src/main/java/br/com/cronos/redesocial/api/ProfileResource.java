package br.com.cronos.redesocial.api;

import br.com.cronos.redesocial.api.dto.CreateProfileRequest;
import br.com.cronos.redesocial.api.dto.CreateUserRequest;
import br.com.cronos.redesocial.domain.model.Profile;
import br.com.cronos.redesocial.domain.repository.ProfileRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/v1/profiles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfileResource {

    private final ProfileRepository profileRepository;

    @Inject
    public ProfileResource(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @POST
    @Transactional
    public Response createProfile(CreateProfileRequest profileRequest) {
        Profile profile = new Profile();
        profile.setName(profileRequest.getName());

        profileRepository.persist(profile);

        return Response.ok(profile).build();
    }

    @GET
    public Response listAllProfiles() {
        PanacheQuery<Profile> query = profileRepository.findAll();
        return Response.ok(query.list()).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteProfile(@PathParam("id") Long id) {
        Profile profile = profileRepository.findById(id);

        if (profile != null) {
            profileRepository.delete(profile);
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, CreateUserRequest userData) {
        Profile profile = profileRepository.findById(id);

        if (profile != null) {
            profile.setName(userData.getName());

            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
