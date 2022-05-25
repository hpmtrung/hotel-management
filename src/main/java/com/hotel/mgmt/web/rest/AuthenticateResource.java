package com.hotel.mgmt.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hotel.mgmt.domain.Account;
import com.hotel.mgmt.repository.AccountRepository;
import com.hotel.mgmt.security.jwt.JWTFilter;
import com.hotel.mgmt.security.jwt.TokenProvider;
import com.hotel.mgmt.service.AccountService;
import com.hotel.mgmt.service.MailService;
import com.hotel.mgmt.service.dto.PublicRegisterDTO;
import com.hotel.mgmt.web.rest.errors.EmailAlreadyUsedException;
import com.hotel.mgmt.web.rest.vm.KeyAndPasswordVM;
import com.hotel.mgmt.web.rest.vm.LoginVM;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/** Controller to authenticate users. */
@RestController
@RequestMapping("/api")
public class AuthenticateResource {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final MailService mailService;

    public AuthenticateResource(
        TokenProvider tokenProvider,
        AuthenticationManagerBuilder authenticationManagerBuilder,
        AccountService accountService,
        AccountRepository accountRepository,
        MailService mailService
    ) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.mailService = mailService;
    }

    /**
     * {@code GET /authenticate} : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request.
     * @return the login if the user is authenticated.
     */
    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginVM.getUsername(),
            loginVM.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    /**
     * {@code POST /register} : register the user.
     *
     * @param registerDTO account register View Model.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody PublicRegisterDTO registerDTO) {
        Account existingAccountWithSameEmail = accountRepository.findByEmail(registerDTO.getEmail()).orElse(null);
        if (existingAccountWithSameEmail != null && existingAccountWithSameEmail.isActivated()) {
            throw new EmailAlreadyUsedException();
        }

        Account account = accountService.registerAccount(registerDTO);
        // Send activation key to register email
        mailService.sendActivationEmail(account);
    }

    /**
     * {@code GET /activate} : activate the registered user.
     *
     * @param key the activation key.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be activated.
     */
    @GetMapping("/activate")
    public void activateAccount(@RequestParam(value = "key") String key) {
        Optional<Account> account = accountService.activateRegistration(key);
        if (account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user was found for this activation key");
        }
    }

    /**
     * {@code POST /account/reset-password/init} : Send an email to reset the password of the user
     *
     * @param email the email of the user
     */
    @PostMapping("/reset-password/init")
    public void initPasswordReset(@RequestBody String email) {
        Optional<Account> account = accountService.requestPasswordReset(email);
        if (account.isPresent()) {
            mailService.sendPasswordResetMail(account.get());
        } else {
            log.warn("Password reset requested for non-existing email");
        }
    }

    /**
     * {@code POST /reset-password/finish} : Finish to reset the password of the user.
     *
     * @param keyAndPasswordVM the generated key and the new password.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if no user is found for the given
     *     reset key
     */
    @PostMapping("/reset-password/finish")
    public void finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPasswordVM) {
        Optional<Account> account = accountService.completePasswordReset(keyAndPasswordVM.getNewPassword(), keyAndPasswordVM.getKey());
        if (account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user was found for this reset key");
        }
    }

    /** Object to return as body in JWT Authentication. */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
