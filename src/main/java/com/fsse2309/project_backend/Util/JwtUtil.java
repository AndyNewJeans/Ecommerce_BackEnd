package com.fsse2309.project_backend.Util;

import com.fsse2309.project_backend.data.user.domainObject.FirebaseUserData;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class JwtUtil {
    public static FirebaseUserData getFirebaseUser(JwtAuthenticationToken jwt){
        return new FirebaseUserData(jwt);
    }
}
