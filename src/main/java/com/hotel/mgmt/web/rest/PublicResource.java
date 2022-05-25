package com.hotel.mgmt.web.rest;

import com.hotel.mgmt.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/public")
public class PublicResource {

    private final FileService fileService;

    public PublicResource(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value = "/image/{entity}/{fileName:.+}")
    public ResponseEntity<Resource> serveImageEntityFile(@PathVariable String entity, @PathVariable String fileName) {
        Resource file = null;
        switch (entity) {
            case "suite":
                file = fileService.loadSuiteImageAsResource(fileName);
                break;
            case "user":
                file = fileService.loadUserImageAsResource(fileName);
                break;
            default:
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setAccessControlAllowOrigin("*");
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(file, headers, HttpStatus.OK);
    }
}
