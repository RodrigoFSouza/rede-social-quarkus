package br.com.cronos.redesocial.domain.repository;

import br.com.cronos.redesocial.domain.model.Profile;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProfileRepository implements PanacheRepository<Profile> {
}
