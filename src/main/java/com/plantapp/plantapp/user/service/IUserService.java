package com.plantapp.plantapp.user.service;

import com.plantapp.plantapp.user.model.User;
import com.plantapp.plantapp.user.model.UserDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface IUserService {
    void addUser(String email, String password, String login);

    Optional<User> getUserById(int userId);

    UserDTO getUserObjectById(int userId);

    void deleteUserById(int userId);

    void updateUser(int userId, String oldPassword, String newPassword, String newEmail, String newLogin, String newPhotoUrl);

    @Transactional
    byte[] getUserProfilePicture(int userId);
    @Transactional
    void updateUserProfilePicture(MultipartFile file, int userId) throws IOException;

}
