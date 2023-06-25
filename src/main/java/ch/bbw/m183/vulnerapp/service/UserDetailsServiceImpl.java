package ch.bbw.m183.vulnerapp.service;

import ch.bbw.m183.vulnerapp.datamodel.UserEntity;
import ch.bbw.m183.vulnerapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = repository.findByUsername(username);

        if (user == null) {
            return null;
        }

        if (Objects.equals(user.getRole(), "ADMIN")) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ADMIN")));
        } else if (Objects.equals(user.getRole(), "USER")) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority("USER")));
        }

        return null;
    }
}
