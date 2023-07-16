package com.example.InstagramBackend.service;

import com.example.InstagramBackend.model.AuthenticationToken;
import com.example.InstagramBackend.model.User;
import com.example.InstagramBackend.repository.IAuthenticationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    IAuthenticationRepo authenticationRepo ;

    public void saveAuthToken(AuthenticationToken authToken) {
        authenticationRepo.save(authToken);
    }

    public boolean authenticate(String email, String token) {
        AuthenticationToken authToken = authenticationRepo.findFirstByTokenValue(token);
    if(authToken == null ){
        return false ;
    }
    String tokenemail = authToken.getUser().getEmail();
    return tokenemail.equals(email);
    }

    public AuthenticationToken findFirstByUser(User user) {
        return authenticationRepo.findFirstByUser(user);
    }

    public void removeToken(AuthenticationToken token) {
        authenticationRepo.delete(token);
    }
}
