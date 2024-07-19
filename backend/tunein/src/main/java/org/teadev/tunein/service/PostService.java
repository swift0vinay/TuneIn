package org.teadev.tunein.service;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teadev.tunein.constants.ErrorMessage;
import org.teadev.tunein.dto.request.CommentEntityRequestDto;
import org.teadev.tunein.dto.request.LikeEntityRequestDto;
import org.teadev.tunein.dto.request.PostEntityRequestDto;
import org.teadev.tunein.dto.request.UnlikeRequestDto;
import org.teadev.tunein.entities.CommentEntity;
import org.teadev.tunein.entities.LikeEntity;
import org.teadev.tunein.entities.PostEntity;
import org.teadev.tunein.entities.UserEntity;
import org.teadev.tunein.exceptions.PostNotFoundException;
import org.teadev.tunein.repository.PostRepository;

import java.util.List;

@Service
@Log4j2
public class PostService {
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private LikeService likeService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private StorageService storageService;
    
    @Autowired
    private CommentService commentService;
    
    public PostEntity createPost(PostEntityRequestDto request) {
        UserEntity user = userService.getUser(request.getUserId());
        
        List<String> filePaths = null;
        
        if (request.getFiles() != null) {
            filePaths = request.getFiles()
                    .stream()
                    .map(item -> storageService.uploadFile(item).getUrl())
                    .toList();
        }
        
        
        String filePathsAsString = filePaths == null ? null : String.join("|", filePaths);
        
        PostEntity post = PostEntity
                .builder()
                .body(request.getBody())
                .user(user)
                .files(filePathsAsString)
                .build();
        
        postRepository.save(post);
        
        return post;
    }
    
    @Transactional
    public PostEntity getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorMessage.POST_NOT_FOUND_MESSAGE));
    }
    
    public List<PostEntity> getPostsByUser(String userId) {
        UserEntity user = userService.getUser(userId);
        
        return postRepository
                .findPostByUser(user)
                .orElse(List.of());
    }
    
    public void deletePost(Long postId) {
        PostEntity postEntity = getPost(postId);
        postRepository.delete(postEntity);
    }
    
    public LikeEntity likePost(LikeEntityRequestDto requestDto) {
        PostEntity post = getPost(requestDto.getPostId());
        UserEntity user = userService.getUser(requestDto.getUserId());
        return likeService.likePost(post, user, requestDto);
    }
    
    public void unlikePost(UnlikeRequestDto requestDto) {
        PostEntity post = getPost(requestDto.getPostId());
        UserEntity user = userService.getUser(requestDto.getUserId());
        likeService.unlikePost(post, user, requestDto);
    }
    
    public List<CommentEntity> findCommentsByPostId(Long postId) {
        PostEntity postEntity = getPost(postId);
        return commentService.findCommentsByPost(postEntity);
    }
    
    public CommentEntity addComment(CommentEntityRequestDto requestDto) {
        PostEntity post = getPost(requestDto.getPostId());
        UserEntity user = userService.getUser(requestDto.getUserId());
        return commentService.addComment(post, user, requestDto);
    }
    
    public void deleteComment(Long commentId) {
        commentService.deleteComment(commentId);
    }
    
    
    public LikeEntity likeComment(LikeEntityRequestDto requestDto) {
        PostEntity post = getPost(requestDto.getPostId());
        UserEntity user = userService.getUser(requestDto.getUserId());
        CommentEntity comment = commentService.getComment(requestDto.getCommentId());
        return likeService.likeComment(post, user, comment, requestDto);
    }
    
    public void unlikeComment(UnlikeRequestDto requestDto) {
        PostEntity post = getPost(requestDto.getPostId());
        UserEntity user = userService.getUser(requestDto.getUserId());
        CommentEntity comment = commentService.getComment(requestDto.getCommentId());
        likeService.unlikeComment(post, user, comment, requestDto);
    }
    
}
