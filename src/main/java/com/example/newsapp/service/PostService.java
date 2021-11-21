package com.example.newsapp.service;

import com.example.newsapp.entity.Post;
import com.example.newsapp.exceptions.RescuersNotFoundEx;
import com.example.newsapp.payload.ApiResponse;
import com.example.newsapp.payload.PostDTO;
import com.example.newsapp.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    final PostRepository postRepository;

    public ApiResponse addNew(PostDTO postDTO) {
        Post post = new Post();
        post.setText(postDTO.getText());
        post.setTitle(postDTO.getTitle());
        postRepository.save(post); // save
        post.setUrl("http://localhost:80/api/post/" + post.getId()); //  add url + id
        postRepository.save(post); // again save
        return new ApiResponse("Added", true);
    }

    public ApiResponse byId(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.map(post -> new ApiResponse("Found", true, post)).orElseThrow(() -> new RescuersNotFoundEx("Post", "id", id));
    }

    public ApiResponse all() {
        return new ApiResponse("Found", true, postRepository.findAll());
    }

    public ApiResponse editPost(Long id, PostDTO postDTO) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (!optionalPost.isPresent()) return new ApiResponse("Post not found", false);
        Post post = optionalPost.get();
        post.setTitle(postDTO.getTitle());
        post.setText(postDTO.getText());
        postRepository.save(post);
        return new ApiResponse("Edited", true);
    }

    public ApiResponse deleteById(Long id) {
        boolean b = postRepository.existsById(id);
        if (!b) return new ApiResponse("Not found", false);
        postRepository.deleteById(id);
        return new ApiResponse("Deletd", true);
    }
}
