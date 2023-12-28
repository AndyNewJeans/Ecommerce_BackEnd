package com.fsse2309.project_backend.service;

import com.fsse2309.project_backend.data.user.domainObject.FirebaseUserData;
import com.fsse2309.project_backend.data.user.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    public UserEntity getEntityByFirebaseUserData(FirebaseUserData firebaseUserData);
}
