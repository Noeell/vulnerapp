package ch.bbw.m183.vulnerapp.service;

import ch.bbw.m183.vulnerapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.util.Scanner;

@Service
@Transactional
@RequiredArgsConstructor
@EnableMethodSecurity //(securedEnabled = true) required but it doesn't work with this
public class HealthService {

    private final UserRepository userRepository;

    @Secured({"ADMIN"})
    @SneakyThrows
    public String health() {
// lookup the local actuator health endpoint, and login as admin, to get extra details
        var url = new URL("http://localhost:8080/actuator/health");
        var urlConnection = url.openConnection();
        var s = new Scanner(urlConnection.getInputStream()).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
