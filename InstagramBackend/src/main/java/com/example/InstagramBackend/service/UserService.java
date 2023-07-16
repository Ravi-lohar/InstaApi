package com.example.InstagramBackend.service;

import com.example.InstagramBackend.model.AuthenticationToken;
import com.example.InstagramBackend.model.Post;
import com.example.InstagramBackend.model.User;
import com.example.InstagramBackend.model.dto.SignInInput;
import com.example.InstagramBackend.model.dto.SignUpOutput;
import com.example.InstagramBackend.repository.IUserRepo;
import com.example.InstagramBackend.service.hasingUtility.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    IUserRepo userRepo ;

    @Autowired
    AuthenticationService authenticationService ;

    @Autowired
    PostService postService;

    public SignUpOutput SignUpUser(User user) {
        boolean signUpStatus = true ;
        String signUpStatusMessage = null ;

        String email = user.getEmail();

        if(email == null ){
            signUpStatusMessage = "Invalid Email" ;
            signUpStatus = false ;
            return new SignUpOutput(signUpStatus , signUpStatusMessage) ;
        }

        User existingUser = userRepo.findFirstByEmail(email) ;

        if(existingUser != null){
            signUpStatusMessage = "User Already Exist" ;
            signUpStatus = false ;
            return new SignUpOutput(signUpStatus , signUpStatusMessage) ;
        }
        try {
            String encryptedPassword = PasswordEncryptor.encryptPassword(user.getPassword());
            user.setPassword(encryptedPassword);
            userRepo.save(user);
            return new SignUpOutput(signUpStatus , "User registered Successfully") ;
        }
        catch (Exception e){
            signUpStatusMessage = "Internal Server Error" ;
            signUpStatus = false ;
            return new SignUpOutput(signUpStatus , signUpStatusMessage) ;

        }
    }

    public String SignInUser(SignInInput signInInput) {
        String signInStatusMessage = null ;
        String signInEmail = signInInput.getEmail();

        if(signInEmail == null){
            signInStatusMessage = "Invalid Email" ;
            return signInStatusMessage ;
        }

        User existingUser = userRepo.findFirstByEmail(signInEmail) ;
        if (existingUser == null ){
            signInStatusMessage = "Email is not registered" ;
            return signInStatusMessage ;
        }
        try {
            String encryptedPassword = PasswordEncryptor.encryptPassword(signInInput.getPassword());
            if(existingUser.getPassword().equals(encryptedPassword)){
                AuthenticationToken authToken = new AuthenticationToken(existingUser);
                authenticationService.saveAuthToken(authToken);
                return "User Signed In Successfully & Token Also Generated " ;
            }
            else {
                signInStatusMessage= "Invalid Credential" ;
                return signInStatusMessage ;
            }
        } catch (Exception e) {
            signInStatusMessage = "Internal Server Error" ;
            return signInStatusMessage ;
        }
    }

    public String signOutUser(String email) {

        User user = userRepo.findFirstByEmail(email);
        AuthenticationToken token = authenticationService.findFirstByUser(user);
        authenticationService.removeToken(token) ;
        return "User Signed Out Successfully" ;
    }

    public String updateUser(User user) {
        Optional<User> existingUser = userRepo.findById(user.getUserId());
        if(existingUser.isPresent()){
            User updatedUser = existingUser.get();
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setAge(user.getAge());
            updatedUser.setPhoneNumber(user.getPhoneNumber());
            userRepo.save(updatedUser);
            return "User Details Updated Successfully" ;
        }
        else {
            return "User not Found" ;
        }
    }

    public String createPost(Post post , String email) {
        User  user = userRepo.findFirstByEmail(email);
        post.setUser(user);
        return postService.addPost(post);
    }

    public Optional<Post> getPost(Integer postId) {
        return postService.getPostById(postId);
    }
}
