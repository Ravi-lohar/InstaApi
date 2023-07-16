package com.example.InstagramBackend.service;

import com.example.InstagramBackend.model.Post;
import com.example.InstagramBackend.repository.IPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    IPostRepo postRepo ;

    public String addPost(Post post) {
        post.setCreatedDate(LocalDateTime.now());
        postRepo.save(post);
        return "Post Created Successfully" ;
    }

    public Optional<Post> getPostById(Integer postId) {
        return postRepo.findById(postId);
    }
}
