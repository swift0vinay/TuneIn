package org.teadev.tunein.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.teadev.tunein.dto.converter.DtoConverter;
import org.teadev.tunein.dto.request.LikeRequestDto;
import org.teadev.tunein.dto.request.PostEntityRequestDto;
import org.teadev.tunein.dto.request.UnlikeRequestDto;
import org.teadev.tunein.dto.response.LikeEntityResponseDto;
import org.teadev.tunein.dto.response.PostEntityResponseDto;
import org.teadev.tunein.entities.LikeEntity;
import org.teadev.tunein.entities.PostEntity;
import org.teadev.tunein.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/post")
@Log4j2
public class PostController {
    
    @Autowired
    PostService postService;
    
    @Autowired
    DtoConverter dtoConverter;
    
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<PostEntityResponseDto> createPost(@ModelAttribute PostEntityRequestDto request) {
        PostEntity post = postService.createPost(request);
        return ResponseEntity.ok(dtoConverter.toDto(post));
    }
    
    
    @GetMapping("/{post_id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<PostEntityResponseDto> getPostByPostId(@PathVariable("post_id") String postId) {
        PostEntity posts = postService.getPost(postId);
        return ResponseEntity.ok(dtoConverter.toDto(posts));
    }
    
    @GetMapping(path = "/user/{user_id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<List<PostEntityResponseDto>> getPostByUserId(@PathVariable("user_id") String userId) {
        List<PostEntity> posts = postService.getPostsByUser(userId);
        return ResponseEntity.ok(dtoConverter.toPostEntityListDto(posts));
    }
    
    @PostMapping(path = "/like")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<LikeEntityResponseDto> likePost(@RequestBody LikeRequestDto requestDto) {
        LikeEntity likeEntity = postService.likePost(requestDto);
        return ResponseEntity.ok(dtoConverter.toDto(likeEntity));
    }
    
    @PostMapping(path = "/unlike")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity unlikePost(@RequestBody UnlikeRequestDto requestDto) {
        postService.unlikePost(requestDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    
}
