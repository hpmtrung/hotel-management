package com.hotel.mgmt.web.rest;

import static com.hotel.mgmt.TestConstants.USER_EMAIL_JWT_TEST;

import com.hotel.mgmt.IntegrationTest;
import com.hotel.mgmt.domain.Account;
import com.hotel.mgmt.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@IntegrationTest
class AuthenticateResourceIT {

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    void testAuthorize() throws Exception {
        Account account = new Account().setEmail(USER_EMAIL_JWT_TEST).setActivated(true).setHashedPassword(passwordEncoder.encode("test"));
        accountRepo.saveAndFlush(account);
    }
}
