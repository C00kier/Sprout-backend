package com.plantapp.plantapp.user_game_progress.service;

import com.plantapp.plantapp.user.model.User;
import com.plantapp.plantapp.user.repository.UserRepository;
import com.plantapp.plantapp.user_game_progress.model.UserGameProgress;
import com.plantapp.plantapp.user_game_progress.repository.UserGameProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserGameProgressService implements IUserGameProgressService {

    private final UserGameProgressRepository userGameProgressRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserGameProgressService(UserGameProgressRepository userGameProgressRepository, UserRepository userRepository) {
        this.userGameProgressRepository = userGameProgressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createUserGameProgressByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        user.ifPresent(value -> userGameProgressRepository.save(new UserGameProgress(0, value)));
    }

    @Override
    public void postUserExperienceByUserId(int userId, int exp) {
        Optional<User> optionalUser = userRepository.findById(userId);
        optionalUser.ifPresent(user -> userGameProgressRepository.save(new UserGameProgress(exp, user)));
    }

    @Override
    public int getUserExperienceByUserId(int userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            Optional<UserGameProgress> optionalUserGameProgress = userGameProgressRepository.findByUser(optionalUser.get());

            if (optionalUserGameProgress.isPresent()) {
                return optionalUserGameProgress.get().getExperience();
            }
        }
        return 0;
    }

    @Override
    @Transactional
    public void removeUserExperienceByUserId(int userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            Optional<UserGameProgress> optionalUserGameProgress = userGameProgressRepository.findByUser(optionalUser.get());
            optionalUserGameProgress.ifPresent(userGameProgressRepository::delete);
        }
    }

    @Override
    public int updateUserExperienceByUserId(int userId, int exp) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            Optional<UserGameProgress> optionalUserGameProgress = userGameProgressRepository.findByUser(optionalUser.get());
            if (optionalUserGameProgress.isPresent()) {
                UserGameProgress userGameProgress = optionalUserGameProgress.get();
                int userExperience = userGameProgress.getExperience();
                int updatedExperience = userExperience + exp;
                userGameProgress.setExperience(updatedExperience);
                userGameProgressRepository.save(userGameProgress);
                return updatedExperience;
            }
        }
        return 0;
    }
}
