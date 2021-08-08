package INKOM.Backend.controller;

import INKOM.Backend.domain.ProfilePicture;
import INKOM.Backend.payload.ProfilePictureDto;
import INKOM.Backend.service.ProfilePictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")

@RequestMapping(value = "/api/image")
public class ProfilePictureController {

    @Autowired
    private ProfilePictureService profilePictureService;

    @PostMapping
    public ResponseEntity<Object> addProfilePicture(@RequestBody ProfilePictureDto profilePictureDto) {

        ProfilePicture profilePicture = profilePictureService.addImage(profilePictureDto);
        return new ResponseEntity<>(profilePicture, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<Object> getProfilePicture(@PathVariable("userId") long userId) {

        ProfilePicture profilePicture = profilePictureService.fetchImage(userId);
        return new ResponseEntity<>(profilePicture, HttpStatus.OK);
    }

}
