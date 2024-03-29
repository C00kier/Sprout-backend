package com.plantapp.plantapp.profile_image.controller;

import com.plantapp.plantapp.profile_image.service.ProfileImageService;
import com.plantapp.plantapp.user.model.User;
import com.plantapp.plantapp.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/image")
public class ProfileImageController {

    private final ProfileImageService profileImageService;
    private final UserRepository userRepository;

    public ProfileImageController(ProfileImageService profileImageService, UserRepository userRepository) {
        this.profileImageService = profileImageService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("userId") int userId) throws IOException {
        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            String response = profileImageService.uploadImage(file, user);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Couldn't upload image. User not found.");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<byte[]> getImageByName(@PathVariable("userId") int userId){
        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            byte[] image = profileImageService.getImage(user);

            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(image);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);
    }
}
