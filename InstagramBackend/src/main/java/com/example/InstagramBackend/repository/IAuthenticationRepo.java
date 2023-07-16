package com.example.InstagramBackend.repository;

import com.example.InstagramBackend.model.AuthenticationToken;
import com.example.InstagramBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthenticationRepo extends JpaRepository<AuthenticationToken , Long> {

    AuthenticationToken findFirstByTokenValue(String token);

    AuthenticationToken findFirstByUser(User user);
}
