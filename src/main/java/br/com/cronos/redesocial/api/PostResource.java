package br.com.cronos.redesocial.api;

import br.com.cronos.redesocial.api.dto.CreatePostRequest;
import br.com.cronos.redesocial.api.dto.PostResponse;
import br.com.cronos.redesocial.domain.model.Post;
import br.com.cronos.redesocial.domain.model.User;
import br.com.cronos.redesocial.domain.repository.FollowerRepository;
import br.com.cronos.redesocial.domain.repository.PostRepository;
import br.com.cronos.redesocial.domain.repository.UserRepository;
import io.quarkus.panache.common.Sort;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Path("/api/v1/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final FollowerRepository followerRepository;

    public PostResource(UserRepository userRepository, PostRepository postRepository, FollowerRepository followerRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.followerRepository = followerRepository;
    }

    @POST
    @Transactional
    public Response savePost(@PathParam("userId") Long userId, CreatePostRequest createPostRequest) {
        User user = userRepository.findById(userId);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Post post = new Post();
        post.setText(createPostRequest.getText());
        post.setUser(user);

        postRepository.persist(post);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response listPosts(@PathParam("userId") Long userId, @HeaderParam("followerId") Long followerId) {
        User user = userRepository.findById(userId);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (isNull(followerId)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("You forgot the header followerId")
                    .build();
        }

        User follower = userRepository.findById(followerId);

        if (isNull(follower)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Inexistent followerId")
                    .build();
        }

        boolean follows = followerRepository.follows(follower, user);
        if (!follows) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("You can´t see these posts")
                    .build();
        }

        var query = postRepository.find("user", Sort.by("dateTime", Sort.Direction.Descending) ,user);
        var list = query.list();

        List<PostResponse> posts = list
                .stream()
                .map(PostResponse::fromDto)
                .collect(Collectors.toList());
        return Response.ok(posts).build();
    }
}
