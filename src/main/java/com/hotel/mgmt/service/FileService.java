package com.hotel.mgmt.service;

import com.hotel.mgmt.domain.Account;
import com.hotel.mgmt.domain.room.SuiteImage;
import com.hotel.mgmt.service.exception.FileNotFoundException;
import com.hotel.mgmt.service.exception.FileServiceException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    private static final Path rootUpLoadDir = Paths.get("upload-img");
    private static final Path userImageUploadDir = Paths.get(rootUpLoadDir.toString(), "user");
    private static final Path suiteImageUploadDir = Paths.get(rootUpLoadDir.toString(), "suite");

    private final Logger log = LogManager.getLogger(FileService.class);

    @Autowired
    public FileService() {
        if (!Files.exists(userImageUploadDir)) {
            try {
                Files.createDirectories(userImageUploadDir);
                log.warn("Couldn't find user image upload directory. Create a new one");
            } catch (IOException e) {
                throw new FileServiceException("Failed to create an empty user image upload directory");
            }
        }
        if (!Files.exists(suiteImageUploadDir)) {
            try {
                Files.createDirectories(suiteImageUploadDir);
                log.warn("Couldn't find suite image upload directory. Create a new " + "one");
            } catch (IOException e) {
                throw new FileServiceException("Failed to create an empty suite image" + " upload directory");
            }
        }
    }

    public static String getUserImageFileNameFromAccount(@NotNull Account account, @NotBlank String fileExtension) {
        if (!fileExtension.contains(".")) {
            throw new IllegalArgumentException("File extension is invalid");
        }
        return account.getId() + fileExtension;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void saveSuiteImage(MultipartFile suiteImage, String intendedFileName) {
        saveFile(suiteImageUploadDir, suiteImage, intendedFileName);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void saveUserImage(MultipartFile userImage, String intendedFileName) {
        saveFile(userImageUploadDir, userImage, intendedFileName);
    }

    @Transactional(readOnly = true)
    public void deleteSuiteImage(SuiteImage suiteImage) throws FileServiceException {
        deleteFile(userImageUploadDir, Objects.requireNonNull(suiteImage).getImageURL());
    }

    @Transactional(readOnly = true)
    public void deleteUserImage(Account user) throws FileServiceException {
        deleteFile(userImageUploadDir, Objects.requireNonNull(user).getImageURL());
    }

    private void saveFile(Path path, MultipartFile file, String intendedFileName) {
        if (file.isEmpty()) {
            throw new FileServiceException("Couldn't save empty file");
        }
        Path destinationFile = path.resolve(Paths.get(intendedFileName)).normalize().toAbsolutePath();

        if (!destinationFile.getParent().equals(path.toAbsolutePath())) {
            throw new FileServiceException("Couldn't store file outside saving directory");
        }

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileServiceException("Failed to save suite image file");
        }
        log.debug("Save file {}", intendedFileName);
    }

    private void deleteFile(Path path, String fileName) throws FileServiceException {
        Path destinationFile = path.resolve(Paths.get(fileName).normalize().toAbsolutePath());

        if (!Files.exists(destinationFile)) {
            throw new FileServiceException("Couldn't find file " + fileName + " in saving location");
        }
        try {
            Files.delete(destinationFile);
        } catch (IOException e) {
            throw new FileServiceException("Failed to delete file");
        }
        log.debug("Delete image {}", fileName);
    }

    private Resource loadAsResource(Path dir, String fileName) {
        try {
            Path file = dir.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException("Could not find file " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not find file " + fileName, e);
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Resource loadSuiteImageAsResource(String fileName) {
        return loadAsResource(suiteImageUploadDir, fileName);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Resource loadUserImageAsResource(String fileName) {
        return loadAsResource(userImageUploadDir, fileName);
    }
}
