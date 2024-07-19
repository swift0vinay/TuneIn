package org.teadev.tunein.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.teadev.tunein.constants.ErrorMessage;
import org.teadev.tunein.dto.converter.DtoConverter;
import org.teadev.tunein.dto.request.CommentEntityRequestDto;
import org.teadev.tunein.dto.request.LikeEntityRequestDto;
import org.teadev.tunein.dto.request.PostEntityRequestDto;
import org.teadev.tunein.dto.request.UnlikeRequestDto;
import org.teadev.tunein.dto.response.CommentEntityResponseDto;
import org.teadev.tunein.dto.response.ErrorResponse;
import org.teadev.tunein.dto.response.LikeEntityResponseDto;
import org.teadev.tunein.dto.response.PostEntityResponseDto;
import org.teadev.tunein.entities.CommentEntity;
import org.teadev.tunein.entities.LikeEntity;
import org.teadev.tunein.entities.PostEntity;
import org.teadev.tunein.service.PostService;
import org.teadev.tunein.validators.MultipartFileExtensionValidator;

import java.util.List;

@RestController
@RequestMapping("/post")
@Log4j2
public class PostController {
    
    @Autowired
    private PostService postService;
    
    @Autowired
    private DtoConverter dtoConverter;
    
    @Autowired
    private MultipartFileExtensionValidator fileExtensionValidator;
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(fileExtensionValidator);
    }
    
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> createPost(@ModelAttribute @Validated PostEntityRequestDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setTitle(ErrorMessage.INVALID_INPUTS_MESSAGE);
            errorResponse.setErrors(errors.stream().map(ObjectError::getDefaultMessage).toList());
            return ResponseEntity.badRequest().body(errorResponse);
        }
        PostEntity post = postService.createPost(request);
        return ResponseEntity.ok(dtoConverter.toDto(post));
    }
    
    
    @DeleteMapping("/{post_id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity removePost(@PathVariable("post_id") Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    
    
    @GetMapping("/{post_id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<PostEntityResponseDto> getPostByPostId(@PathVariable("post_id") Long postId) {
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
    public ResponseEntity<LikeEntityResponseDto> likePost(@RequestBody LikeEntityRequestDto requestDto) {
        LikeEntity likeEntity = postService.likePost(requestDto);
        return ResponseEntity.ok(dtoConverter.toDto(likeEntity));
    }
    
    @PostMapping(path = "/unlike")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity unlikePost(@RequestBody UnlikeRequestDto requestDto) {
        postService.unlikePost(requestDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    
    
    @GetMapping(path = "/{post_id}/comments")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<List<CommentEntityResponseDto>> findAllCommentsOnPost(@PathVariable("post_id") Long postId) {
        List<CommentEntity> comments = postService.findCommentsByPostId(postId);
        return ResponseEntity.ok(dtoConverter.toCommentEntityListDto(comments));
    }
    
    @PostMapping(path = "/comment")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<CommentEntityResponseDto> addComment(@RequestBody CommentEntityRequestDto requestDto) {
        CommentEntity commentEntity = postService.addComment(requestDto);
        return ResponseEntity.ok(dtoConverter.toDto(commentEntity));
    }
    
    @DeleteMapping(path = "/comment/{comment_id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity deleteComment(@PathVariable("comment_id") Long commentId) {
        postService.deleteComment(commentId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    
    @PostMapping(path = "/comment/like")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<LikeEntityResponseDto> likeComment(@RequestBody LikeEntityRequestDto requestDto) {
        LikeEntity likeEntity = postService.likeComment(requestDto);
        return ResponseEntity.ok(dtoConverter.toDto(likeEntity));
    }
    
    @PostMapping(path = "/comment/unlike")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity unlikeComment(@RequestBody UnlikeRequestDto requestDto) {
        postService.unlikeComment(requestDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    
    
}
