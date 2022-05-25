package com.hotel.mgmt.repository;

import com.hotel.mgmt.domain.Account;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    String ACCOUNTS_BY_EMAIL_CACHE = "usersByEmail";

    @Query("select a from Account a join fetch Authority au on a.authority = au")
    Optional<Account> findById(Long id);

    Optional<Account> findByActivationKey(String activationKey);

    List<Account> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndResetDate(Instant dateTime);

    Optional<Account> findByResetKey(String resetKey);

    @Cacheable(cacheNames = ACCOUNTS_BY_EMAIL_CACHE)
    @Query("select a from Account a join fetch Authority au on a.authority = au")
    Optional<Account> findByEmail(String email);
}
