package com.lets_play_e_comerce_platform.lets_play.security;

import com.lets_play_e_comerce_platform.lets_play.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repo;
    public CustomUserDetailsService(UserRepository repo) { this.repo = repo; }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return Optional.ofNullable(repo.findFirstByEmail(email))
                   .map(UserPrincipal::new)
                   .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }
}
