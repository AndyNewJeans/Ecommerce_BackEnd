package com.fsse2309.project_backend.service.impl;

import com.fsse2309.project_backend.data.user.domainObject.FirebaseUserData;
import com.fsse2309.project_backend.data.user.entity.UserEntity;
import com.fsse2309.project_backend.repository.UserRepository;
import com.fsse2309.project_backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getEntityByFirebaseUserData(FirebaseUserData firebaseUserData) {
        return userRepository.getUserEntityByFirebaseUid(firebaseUserData.getFirebaseUid())
                .orElseGet(() -> userRepository.save(new UserEntity(firebaseUserData)));
    }
}