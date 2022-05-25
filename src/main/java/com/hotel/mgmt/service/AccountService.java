package com.hotel.mgmt.service;

import com.hotel.mgmt.config.Constants;
import com.hotel.mgmt.domain.Account;
import com.hotel.mgmt.repository.AccountRepository;
import com.hotel.mgmt.repository.AuthorityRepository;
import com.hotel.mgmt.security.AuthoritiesConstants;
import com.hotel.mgmt.security.SecurityUtils;
import com.hotel.mgmt.service.dto.PublicRegisterDTO;
import com.hotel.mgmt.service.dto.UserAccountDTO;
import com.hotel.mgmt.service.exception.InvalidPasswordServiceException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.security.RandomUtil;

@Service
@Transactional
public class AccountService {

    private final Logger log = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    private final CacheManager cacheManager;

    public AccountService(
        AccountRepository accountRepository,
        PasswordEncoder passwordEncoder,
        AuthorityRepository authorityRepository,
        CacheManager cacheManager
    ) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.cacheManager = cacheManager;
    }

    public Optional<Account> activateRegistration(String key) {
        log.debug("Activating account for activation key {}", key);
        return accountRepository
            .findByActivationKey(key)
            .map(account -> {
                account.setActivated(true);
                account.setActivationKey(null);
                clearAccountCaches(account);
                log.debug("Activated account {}", account);
                return account;
            });
    }

    public Optional<Account> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {},", key);
        return accountRepository
            .findByResetKey(key)
            .filter(account -> account.getResetDate().isAfter(Instant.now().minus(1, ChronoUnit.DAYS)))
            .map(account -> {
                account.setHashedPassword(passwordEncoder.encode(newPassword)).setResetKey(null).setResetDate(null);
                clearAccountCaches(account);
                return account;
            });
    }

    public Optional<Account> requestPasswordReset(String email) {
        return accountRepository
            .findByEmail(email)
            .filter(Account::isActivated)
            .map(account -> {
                account.setResetKey(RandomUtil.generateResetKey()).setResetDate(Instant.now());
                clearAccountCaches(account);
                return account;
            });
    }

    public Account registerAccount(PublicRegisterDTO registerDTO) {
        accountRepository.findByEmail(registerDTO.getEmail()).ifPresent(this::removeNonActivateAccount);
        Account newAccount = new Account()
            .setHashedPassword(passwordEncoder.encode(registerDTO.getPassword()))
            .setFullName(registerDTO.getFullName())
            .setPhone(registerDTO.getPhone())
            .setEmail(registerDTO.getEmail())
            .setActivated(false)
            .setActivationKey(RandomUtil.generateActivationKey());
        authorityRepository.findByName(AuthoritiesConstants.CUSTOMER).ifPresent(newAccount::setAuthority);
        accountRepository.save(newAccount);
        clearAccountCaches(newAccount);
        return newAccount;
    }

    public void updateAccountByUser(UserAccountDTO accountDTO) {
        getLoginAccount()
            .ifPresent(account -> {
                account
                    .setFullName(accountDTO.getFullName())
                    .setEmail(accountDTO.getEmail())
                    .setPhone(accountDTO.getPhone())
                    .setAddress(accountDTO.getAddress());
                clearAccountCaches(account);
                log.debug("Update (by user) account {}", account);
            });
    }

    public Account createAccountByAdmin(UserAccountDTO accountDTO) {
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());

        Account newAccount = new Account()
            .setFullName(accountDTO.getFullName())
            .setEmail(accountDTO.getEmail())
            .setPhone(accountDTO.getPhone())
            .setAddress(accountDTO.getAddress())
            .setLangKey(Constants.DEFAULT_LANGUAGE)
            .setHashedPassword(encryptedPassword)
            .setResetDate(Instant.now())
            .setResetKey(RandomUtil.generateResetKey())
            .setActivated(true)
            .setAuthority(authorityRepository.findByName(accountDTO.getAuthority()).orElseThrow());

        accountRepository.save(newAccount);
        clearAccountCaches(newAccount);
        log.debug("Create (by Admin) account {}", newAccount);
        return newAccount;
    }

    public void updateAccountByAdmin(UserAccountDTO accountDTO) {
        Account account = accountRepository.findById(accountDTO.getId()).orElseThrow();
        account
            .setFullName(accountDTO.getFullName())
            .setPhone(accountDTO.getPhone())
            .setEmail(accountDTO.getEmail())
            .setAddress(accountDTO.getAddress())
            .setAuthority(authorityRepository.findByName(accountDTO.getAuthority()).orElseThrow());
        accountRepository.save(account);
        clearAccountCaches(account);
        log.debug("Update (by Admin) account {}", account);
    }

    public void changePassword(String currentClearTextPassword, String newPassword) {
        getLoginAccount()
            .ifPresent(account -> {
                String currentEncrypedPassword = account.getHashedPassword();
                if (!passwordEncoder.matches(currentClearTextPassword, currentEncrypedPassword)) {
                    throw new InvalidPasswordServiceException();
                }
                String encryptedPassword = passwordEncoder.encode(newPassword);
                account.setHashedPassword(encryptedPassword);
                clearAccountCaches(account);
                log.debug("Change password for Account {}", account);
            });
    }

    @Transactional(readOnly = true)
    public Optional<Account> getLoginAccount() {
        return SecurityUtils.getCurrentUserLogin().flatMap(accountRepository::findByEmail);
    }

    @Transactional(readOnly = true)
    public Optional<Account> getAccountById(long accountId) {
        return Optional.of(accountRepository.findById(accountId)).filter(Optional::isPresent).map(Optional::get);
    }

    @Transactional(readOnly = true)
    public Page<UserAccountDTO> getAllManagedAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable).map(UserAccountDTO::new);
    }

    /**
     * Not activated accounts should be automatically deleted after 3 days.
     *
     * <p>This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNonActivatedAccounts() {
        accountRepository
            .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndResetDate(Instant.now().minus(3, ChronoUnit.DAYS))
            .forEach(account -> {
                log.debug("Deleting not activated account {}", account.getEmail());
                accountRepository.delete(account);
                this.clearAccountCaches(account);
            });
    }

    private boolean removeNonActivateAccount(Account existingAccount) {
        if (existingAccount.isActivated()) {
            return false;
        }
        accountRepository.delete(existingAccount);
        accountRepository.flush();
        clearAccountCaches(existingAccount);
        return true;
    }

    private void clearAccountCaches(Account account) {
        Objects.requireNonNull(cacheManager.getCache(AccountRepository.ACCOUNTS_BY_EMAIL_CACHE)).evict(account.getEmail());
    }
}
