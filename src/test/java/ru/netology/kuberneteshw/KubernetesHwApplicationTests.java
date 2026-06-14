package ru.netology.kuberneteshw;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
class KubernetesHwApplicationTests {

    @Container
    public static GenericContainer<?> devApp = new GenericContainer<>("devapp:latest")
            .withExposedPorts(8080);

    @Container
    public static GenericContainer<?> prodApp = new GenericContainer<>("prodapp:latest")
            .withExposedPorts(8081);
    private final RestTemplate restTemplate = new RestTemplate();

    @BeforeAll
    public static void setUp() {
    }

    @Test
    void devProfileTest() {
        Integer devPort = devApp.getMappedPort(8080);
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + devPort + "/profile", String.class);
        System.out.println("Dev response: " + response.getBody());
        assertEquals("Current profile is dev", response.getBody());
    }

    @Test
    void prodProfileTest() {
        Integer prodPort = prodApp.getMappedPort(8081);
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + prodPort + "/profile", String.class);
        System.out.println("Prod response: " + response.getBody());
        assertEquals("Current profile is production", response.getBody());
    }
}