package gcu.adweb.codecrew_prototype.application;

import gcu.adweb.codecrew_prototype.domain.Member;
import gcu.adweb.codecrew_prototype.domain.Post;
import gcu.adweb.codecrew_prototype.domain.Tag;
import gcu.adweb.codecrew_prototype.dto.PostDto;
import gcu.adweb.codecrew_prototype.dto.PostDto.*;
import gcu.adweb.codecrew_prototype.dto.TagDto;
import gcu.adweb.codecrew_prototype.repository.MemberRepository;
import gcu.adweb.codecrew_prototype.repository.PostRepository;
import gcu.adweb.codecrew_prototype.repository.SearchPostRepository;
import gcu.adweb.codecrew_prototype.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final SearchPostRepository searchPostRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;


    //포스트 저장

    public PostResponseDto savePost(SavePostDto savePostDto,Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(()->new RuntimeException());

        Post post = Post.builder( )
                .title(savePostDto.getTitle())
                .content(savePostDto.getContent())
                .codeBeforeUpdate(savePostDto.getCodeBeforeUpdate())
                .codeAfterUpdate(savePostDto.getCodeAfterUpdate())
                .reference(savePostDto.getReference())
                .privacy(savePostDto.getPrivacy())
                .postImg(savePostDto.getPostImg())
                .member(member)
                .build( );

        List<String> tagList = savePostDto.getTagDtoList();
        List<TagDto> tagDtoList = new ArrayList<>(  );
        for(String name : tagList) {
            Tag tag = Tag.builder( )
                    .name(name)
                    .build( );
            post.getTagList().add(tag);
            tagDtoList.add(TagDto.toTagDto(tag,name));

        }

        postRepository.save(post);


        // 태그 한번에 저장

        return PostResponseDto.toPostResponseDto(post,tagDtoList);

    }

    // 포스트 특정 조건으로 전체 조회
    // keyword 포함하는거 또는 memberId 포함하는 것만 조회 가능

    public Page<PostResponseDto> postList(String keyword, Pageable pageable, Long memberId) {
        Page<PostResponseDto> result = searchPostRepository.findPostByKeyword(keyword,pageable,memberId);
        return result;
    }



}
