package app.services;

import app.repositories.UserRepository;
import app.entities.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;

    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new UserDetailsImpl(repository.findByEmail(s));
    }
}
