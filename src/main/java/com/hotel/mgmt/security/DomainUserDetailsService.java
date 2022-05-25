package com.hotel.mgmt.security;

import com.hotel.mgmt.domain.Account;
import com.hotel.mgmt.repository.AccountRepository;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** Authenticate a user from the database. */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final AccountRepository accountRepository;

    public DomainUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) {
        log.debug("Authenticating {}", email);

        return accountRepository
            .findByEmail(email)
            .map(user -> createSpringSecurityUser(email, user))
            .orElseThrow(() -> new UsernameNotFoundException("User with email '" + email + "' was not found in the database"));
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String email, Account account) {
        if (!account.isActivated()) {
            throw new UserNotActivatedException("Account with email '" + email + "' was not activated");
        }
        List<GrantedAuthority> grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(account.getAuthority().getName()));
        return new org.springframework.security.core.userdetails.User(account.getEmail(), account.getHashedPassword(), grantedAuthorities);
    }
}
