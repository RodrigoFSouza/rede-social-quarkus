package br.com.cronos.redesocial.api;

import br.com.cronos.redesocial.api.dto.CreateProfileRequest;
import br.com.cronos.redesocial.api.dto.ResponseError;
import br.com.cronos.redesocial.domain.model.Profile;
import br.com.cronos.redesocial.domain.repository.ProfileRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

import static java.util.Objects.isNull;

@Path("/api/v1/profiles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfileResource {

    private final Validator validator;

    private final ProfileRepository profileRepository;

    @Inject
    public ProfileResource(Validator validator, ProfileRepository profileRepository) {
        this.validator = validator;
        this.profileRepository = profileRepository;
    }

    @POST
    @Transactional
    public Response createProfile(CreateProfileRequest profileRequest) {
        Set<ConstraintViolation<CreateProfileRequest>> violations = validator.validate(profileRequest);
        if (!violations.isEmpty()) {
            return ResponseError
                    .createFromValidation(violations)
                    .withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }

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
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateProfile(@PathParam("id") Long id, CreateProfileRequest profileRequest) {
        Profile profile = profileRepository.findById(id);

        Set<ConstraintViolation<CreateProfileRequest>> violations = validator.validate(profileRequest);
        if (!violations.isEmpty()) {
            return ResponseError
                    .createFromValidation(violations)
                    .withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }

        if (isNull(profile)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        profile.setName(profileRequest.getName());
        profileRepository.persist(profile);

        return Response.noContent().build();
    }
}
