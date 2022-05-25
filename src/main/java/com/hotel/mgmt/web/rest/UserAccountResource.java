package com.hotel.mgmt.web.rest;

import com.hotel.mgmt.domain.Account;
import com.hotel.mgmt.repository.AccountRepository;
import com.hotel.mgmt.service.AccountService;
import com.hotel.mgmt.service.FileService;
import com.hotel.mgmt.service.dto.UserAccountDTO;
import com.hotel.mgmt.web.rest.errors.BadRequestException;
import com.hotel.mgmt.web.rest.errors.EmailAlreadyUsedException;
import com.hotel.mgmt.web.rest.vm.UserPasswordChangeVM;
import java.util.Objects;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/** REST controller for managing the current user's account. */
@RestController
@RequestMapping("/api/account")
public class UserAccountResource {

    private final Logger log = LoggerFactory.getLogger(UserAccountResource.class);

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final FileService fileService;

    public UserAccountResource(AccountService accountService, AccountRepository accountRepository, FileService fileService) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.fileService = fileService;
    }

    /**
     * {@code GET /account} : get the current user.
     *
     * @return the current user.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be found.
     */
    @GetMapping
    public UserAccountDTO getAccount() {
        final String userImageURLPrefix = ResourceUtil.getUserImageBaseURL();
        return accountService
            .getLoginAccount()
            .map(account ->
                new UserAccountDTO()
                    .setFullName(account.getFullName())
                    .setEmail(account.getEmail())
                    .setPhone(account.getPhone())
                    .setAddress(account.getAddress())
                    .setLangKey(account.getLangKey())
                    .setActivated(account.isActivated())
                    .setImageURL(userImageURLPrefix + account.getImageURL())
                    .setAuthority(account.getAuthority().getName())
            )
            .orElseThrow(() -> new UserAccountResourceException("User couldn't be found"));
    }

    @PutMapping("/info")
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
    public void saveAccountInfo(@Valid @RequestBody UserAccountDTO accountDTO) {
        final Account loginUser = accountService.getLoginAccount().orElseThrow(UserAccountResourceException::new);

        // check email should be not duplicated
        final Account existingAccountWithSameEmail = accountRepository.findByEmail(loginUser.getEmail()).orElse(null);
        if (existingAccountWithSameEmail != null && !existingAccountWithSameEmail.getId().equals(loginUser.getId())) {
            throw new EmailAlreadyUsedException();
        }

        accountService.updateAccountByUser(accountDTO);
    }

    @PutMapping("/image")
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
    public void saveAccountImage(@RequestParam("image") MultipartFile image) {
        final Account loginUser = accountService.getLoginAccount().orElseThrow();

        String originFileName = image.getOriginalFilename();
        if (!Objects.requireNonNull(originFileName).contains(".")) {
            throw new BadRequestException("User upload image name is invalid: '" + originFileName + "'", "invalidUpload");
        }

        // Save updated avatar with name formated in pattern
        String fileName = loginUser.getId() + originFileName.substring(originFileName.lastIndexOf("."));

        if (loginUser.getImageURL() != null) {
            fileService.deleteUserImage(loginUser);
        }
        fileService.saveUserImage(image, fileName);

        loginUser.setImageURL(fileName);
        accountRepository.saveAndFlush(loginUser);
    }

    /**
     * {@code POST /account/change-password} : change the current user's password.
     *
     * @param userPasswordChangeVM current and new password.
     */
    @PostMapping("/change-password")
    public void changePassword(@Valid @RequestBody UserPasswordChangeVM userPasswordChangeVM) {
        accountService.changePassword(userPasswordChangeVM.getCurrentPassword(), userPasswordChangeVM.getNewPassword());
    }

    private static class UserAccountResourceException extends RuntimeException {

        private static final long serialVersionUID = -4174458759247500666L;

        private UserAccountResourceException() {}

        private UserAccountResourceException(String message) {
            super(message);
        }
    }
}
