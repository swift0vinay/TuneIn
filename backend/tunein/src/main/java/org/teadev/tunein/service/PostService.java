package org.teadev.tunein.service;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teadev.tunein.dto.request.LikeRequestDto;
import org.teadev.tunein.dto.request.PostEntityRequestDto;
import org.teadev.tunein.entities.LikeEntity;
import org.teadev.tunein.entities.PostEntity;
import org.teadev.tunein.entities.UserEntity;
import org.teadev.tunein.exceptions.PostNotFoundException;
import org.teadev.tunein.repository.LikeRepository;
import org.teadev.tunein.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class PostService {
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private LikeRepository likeRepository;
    
    @Autowired
    private UserService userService;
    
    
    @Autowired
    private StorageService storageService;
    
    
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
    public PostEntity getPost(String postId) {
        return postRepository.findById(Integer.parseInt(postId))
                .orElseThrow(() -> new PostNotFoundException("No post found with the requested id"));
    }
    
    public List<PostEntity> getPostsByUser(String userId) {
        UserEntity user = userService.getUser(userId);
        
        return postRepository
                .findPostByUser(user)
                .orElse(List.of());
    }
    
    @Transactional
    public LikeEntity likePost(LikeRequestDto request) {
        PostEntity postEntity = getPost(request.getPostId());
        UserEntity userEntity = userService.getUser(request.getUserId());
        
        // check if post is already liked by the same user
        Optional<LikeEntity> optionalLikeEntity = likeRepository.findByPostAndUser(postEntity, userEntity);
        if (optionalLikeEntity.isPresent()) {
            return optionalLikeEntity.get();
        }
        
        LikeEntity likeEntity = LikeEntity
                .builder()
                .post(postEntity)
                .user(userEntity)
                .build();
        return likeRepository.save(likeEntity);
    }
    
}
