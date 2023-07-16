package com.example.InstagramBackend.controller;

import com.example.InstagramBackend.model.Post;
import com.example.InstagramBackend.model.User;
import com.example.InstagramBackend.model.dto.SignInInput;
import com.example.InstagramBackend.model.dto.SignUpOutput;
import com.example.InstagramBackend.service.AuthenticationService;
import com.example.InstagramBackend.service.UserService;
import jakarta.validation.Valid;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Validated
public class UserController {
    @Autowired
    UserService userService ;

    @Autowired
    AuthenticationService authenticationService ;

    @PostMapping("user/signUp")
    public SignUpOutput SignUpUser (@Valid @RequestBody User user ){
        return userService.SignUpUser(user) ;
    }

    @PostMapping("user/signIn")
    public String SignInUser (@Valid @RequestBody SignInInput signInInput){
        return userService.SignInUser(signInInput) ;
    }

    @DeleteMapping("user/signOut/{email}/{token}")
    public String signOutUser(@Valid @PathVariable  String email , @Valid @PathVariable String token){

        if(authenticationService.authenticate(email , token)){
            return userService.signOutUser(email);
        }
        else {
            return "Sign out s not allowed for non authenticated user" ;
        }
    }

    @PutMapping("user/update/{email}/{token}")
    public String updateUser (@Valid @PathVariable  String email , @Valid @PathVariable String token ,@Valid @RequestBody User user )
    {
        if(authenticationService.authenticate(email , token)){
            return userService.updateUser(user) ;
        }
        else {
            return "Update is not allowed for non authenticated user" ;
        }
    }

    @PostMapping("user/createPost/{email}/{token}")
    public String createPost (@Valid @PathVariable  String email , @Valid @PathVariable String token ,@Valid @RequestBody Post post )
    {
        if(authenticationService.authenticate(email , token)){
            return userService.createPost(post , email);
        }
        else {
            return "Post creation is not allowed for non authenticated user" ;
        }
    }

    @GetMapping("user/getPost/{email}/{token}/{postId}")
    public Optional<Post> getPost (@Valid @PathVariable  String email , @Valid @PathVariable String token , @PathVariable Integer postId )
    {
        if(authenticationService.authenticate(email , token)){
            return userService.getPost(postId);
        }
        else {
            throw new RuntimeException("Post Visiting is not allowed for non authenticated user") ;
        }
    }
}
