package br.com.cronos.redesocial.domain.repository;

import br.com.cronos.redesocial.domain.model.Follower;
import br.com.cronos.redesocial.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower> {
    public boolean follows(User follower, User user) {

        Map<String, Object> params = Parameters.with("follower", follower).and("user", user).map();

        PanacheQuery<Follower> query = find("follower = :follwer and user = :user", params);

        Optional<Follower> result = query.firstResultOptional();

        return result.isPresent();
    }
}
