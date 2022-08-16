package br.com.cronos.redesocial.api;

import br.com.cronos.redesocial.api.dto.FollowerRequest;
import br.com.cronos.redesocial.domain.model.Follower;
import br.com.cronos.redesocial.domain.repository.FollowerRepository;
import br.com.cronos.redesocial.domain.repository.UserRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/v1/users/{userId}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerResource {

    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;

    @Inject
    public FollowerResource(FollowerRepository followerRepository, UserRepository userRepository) {
        this.followerRepository = followerRepository;
        this.userRepository = userRepository;
    }

    @PUT
    @Transactional
    public Response follower(@PathParam("userId") Long userId, FollowerRequest request) {
        if (userId.equals(request.getFollowerId())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("You can't follower yorself")
                    .build();
        }

        var user = userRepository.findById(userId);

        if (user == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("User not found of id " + userId)
                    .build();
        }

        var follower = userRepository.findByIdOptional(request.getFollowerId());
        if (follower.isEmpty()) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Follower not found of id " + request.getFollowerId())
                    .build();
        }

        boolean follows = followerRepository.follows(follower.get(), user);

        if (!follows) {
            var entity = new Follower();
            entity.setUser(user);
            entity.setFollower(follower.get());
            followerRepository.persist(entity);
        }

        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
