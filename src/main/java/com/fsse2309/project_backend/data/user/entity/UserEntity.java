package com.fsse2309.project_backend.data.user.entity;

import com.fsse2309.project_backend.data.user.domainObject.FirebaseUserData;
import jakarta.persistence.*;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Table(name="user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;
    @Column(name="firebase_uid", nullable=false, unique = true)
    private String firebaseUid;
    @Column(name="email", nullable=false, unique = true)
    private String email;

    public UserEntity() {
    }

    public UserEntity(FirebaseUserData data){
        this.firebaseUid = data.getFirebaseUid();
        this.email = data.getEmail();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getFirebaseUid() {
        return firebaseUid;
    }

    public void setFirebaseUid(String firebaseUid) {
        this.firebaseUid = firebaseUid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
