package app.services;

import app.repositories.AccountRepository;
import app.entities.account.AccountDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountRepository repository;

    public UserDetailsServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new AccountDetailsImpl(repository.getAccountByEmail(s));
    }
}
