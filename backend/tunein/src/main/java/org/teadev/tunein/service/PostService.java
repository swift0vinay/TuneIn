package org.teadev.tunein.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teadev.tunein.constants.ErrorMessage;
import org.teadev.tunein.dto.request.PostEntityRequestDto;
import org.teadev.tunein.entities.PostEntity;
import org.teadev.tunein.entities.UserEntity;
import org.teadev.tunein.exceptions.PostNotFoundException;
import org.teadev.tunein.exceptions.UserNotFoundException;
import org.teadev.tunein.repository.PostRepository;
import org.teadev.tunein.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class PostService {
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PostRepository postRepository;
    
    @Autowired
    StorageService storageService;
    
    public PostEntity createPost(PostEntityRequestDto request) {
        UserEntity user = userRepository
                .findById(UUID.fromString(request.getUserId()))
                .orElseThrow(() -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND_MESSAGE));
        
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
    
    public PostEntity getPosts(String postId) {
        return postRepository.findById(Integer.parseInt(postId))
                .orElseThrow(() -> new PostNotFoundException("No post found with the requested id"));
    }
    
    public List<PostEntity> getPostsByUser(String userId) {
        UserEntity user = userRepository
                .findById(UUID.fromString(userId))
                .orElseThrow(() -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND_MESSAGE));
        
        return postRepository
                .findPostByUser(user)
                .orElse(List.of());
    }
    
}
