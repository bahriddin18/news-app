package com.example.newsapp.controller;

import com.example.newsapp.aop.CheckPermission;
import com.example.newsapp.payload.ApiResponse;
import com.example.newsapp.payload.CommentDTO;
import com.example.newsapp.repository.CommentRepository;
import com.example.newsapp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    final CommentRepository commentRepository;

    final CommentService commentService;

    @CheckPermission("ADD_COMMENT")
    @PostMapping
    public HttpEntity<?> addComment(@RequestBody CommentDTO commentDTO) {
        ApiResponse apiResponse = commentService.add(commentDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("EDIT_COMMENT")
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        ApiResponse apiResponse = commentService.edit(commentDTO, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("DELETE_MY_COMMENT")
    @DeleteMapping("/myComment/{id}")
    public HttpEntity<?> deleteMyComment(@PathVariable Long id) {
        ApiResponse apiResponse = commentService.deleteMyComment(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("DELETE_COMMENT")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Long id) {
        ApiResponse apiResponse = commentService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        ApiResponse apiResponse = commentService.getById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        ApiResponse apiResponse = commentService.getAll();
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
