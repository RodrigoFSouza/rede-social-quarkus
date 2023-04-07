package br.com.cronos.redesocial.api;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Collections;
import java.util.Map;

public class RedeSocialTestLifecycleManager implements QuarkusTestResourceLifecycleManager {

    private final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15.2")
            .withDatabaseName("rede-social")
            .withUsername("postgres")
            .withPassword("postgres");

    @Override
    public Map<String, String> start() {
        postgresContainer.start();

        return Collections.singletonMap("quarkus.datasource.url", postgresContainer.getJdbcUrl());
    }

    @Override
    public void stop() {
        if (postgresContainer != null && postgresContainer.isRunning()) {
            postgresContainer.stop();
        }
    }
}
