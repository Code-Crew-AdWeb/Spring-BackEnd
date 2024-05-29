package gcu.adweb.codecrew_prototype.application;

import gcu.adweb.codecrew_prototype.domain.Post;
import gcu.adweb.codecrew_prototype.dto.PostDto;
import gcu.adweb.codecrew_prototype.dto.PostDto.*;
import gcu.adweb.codecrew_prototype.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;



    public void savePost(SavePostDto savePostDto) {

        Post post = Post.builder( )
                .title(savePostDto.getTitle())
                .content(savePostDto.getContent())
                .code(savePostDto.getCode())
                .privacy(savePostDto.getPrivacy()).build( );
        postRepository.save(post);
    }

}
