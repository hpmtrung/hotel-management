package com.hotel.mgmt.web.rest;

import com.hotel.mgmt.domain.Account;
import com.hotel.mgmt.repository.AccountRepository;
import com.hotel.mgmt.service.AccountService;
import com.hotel.mgmt.service.MailService;
import com.hotel.mgmt.service.dto.UserAccountDTO;
import com.hotel.mgmt.web.rest.errors.AccountIdNotFoundException;
import com.hotel.mgmt.web.rest.errors.BadRequestException;
import com.hotel.mgmt.web.rest.errors.EmailAlreadyUsedException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/accounts")
public class AdminAccountResource {

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = List.of(
        "id",
        "firstName",
        "lastName",
        "email",
        "phone",
        "address",
        "activated",
        "langKey"
    );

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final MailService mailService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public AdminAccountResource(AccountService accountService, AccountRepository accountRepository, MailService mailService) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.mailService = mailService;
    }

    @GetMapping
    public ResponseEntity<List<UserAccountDTO>> getAllUsers(@ParameterObject @PageableDefault Pageable pageable) {
        log.debug("REST request to get all User for an admin");
        if (!onlyContainsAllowedProperties(pageable)) {
            throw new BadRequestException("Params of pagination contains unallowed properties", "invalidPagination");
        }

        final Page<UserAccountDTO> page = accountService.getAllManagedAccounts(pageable);
        final String userImagePrefix = ResourceUtil.getUserImageBaseURL();
        page.map(adminAccountDTO -> adminAccountDTO.setImageURL(userImagePrefix + adminAccountDTO.getImageURL()));
        return new ResponseEntity<>(page.getContent(), ResourceUtil.getHeaderFromPage(page), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAccountDTO> getUser(@PathVariable Long id) {
        log.debug("REST request to get User id : {}", id);
        final String userImagePrefix = ResourceUtil.getUserImageBaseURL();
        Account account = accountService.getAccountById(id).orElseThrow(AccountIdNotFoundException::new);

        UserAccountDTO adminAccountDTO = new UserAccountDTO(account);
        adminAccountDTO.setImageURL(userImagePrefix + adminAccountDTO.getImageURL());
        return ResponseEntity.ok(adminAccountDTO);
    }

    @PostMapping
    public ResponseEntity<UserAccountDTO> createUser(@RequestBody @Valid UserAccountDTO accountDTO) throws URISyntaxException {
        log.debug("REST request to save User : {}", accountDTO);

        if (accountRepository.findByEmail(accountDTO.getEmail().toLowerCase()).isPresent()) {
            throw new EmailAlreadyUsedException();
        }

        Account newUser = accountService.createAccountByAdmin(accountDTO);
        mailService.sendCreationEmail(newUser);

        UserAccountDTO adminAccountDTO = new UserAccountDTO(newUser);

        final String userImagePrefix = ResourceUtil.getUserImageBaseURL();
        adminAccountDTO.setImageURL(userImagePrefix + newUser.getImageURL());
        return ResponseEntity.created(new URI("/api/admin/accounts/" + adminAccountDTO.getId())).body(adminAccountDTO);
    }

    @PutMapping
    public void updateUser(@RequestBody @Valid UserAccountDTO accountDTO) {
        log.debug("REST request to update User : {}", accountDTO);
        Account account = accountRepository.findById(accountDTO.getId()).orElseThrow(AccountIdNotFoundException::new);
        final String userImagePrefix = ResourceUtil.getUserImageBaseURL();
        accountService.updateAccountByAdmin(accountDTO);
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }
}
