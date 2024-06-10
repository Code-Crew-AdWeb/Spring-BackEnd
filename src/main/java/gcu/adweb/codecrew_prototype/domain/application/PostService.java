package gcu.adweb.codecrew_prototype.domain.application;

import gcu.adweb.codecrew_prototype.domain.dto.PostDto;
import gcu.adweb.codecrew_prototype.domain.dto.TagDto;
import gcu.adweb.codecrew_prototype.domain.entity.Member;
import gcu.adweb.codecrew_prototype.domain.entity.Post;
import gcu.adweb.codecrew_prototype.domain.entity.Tag;
import gcu.adweb.codecrew_prototype.domain.repository.MemberRepository;
import gcu.adweb.codecrew_prototype.domain.repository.PostRepository;
import gcu.adweb.codecrew_prototype.domain.repository.SearchPostRepository;
import gcu.adweb.codecrew_prototype.domain.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final SearchPostRepository searchPostRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;


    //포스트 저장

    @Transactional
    public PostDto.PostResponseDto savePost(PostDto.SavePostDto savePostDto, Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(()->new RuntimeException());

        Post post = Post.builder( )
                .title(savePostDto.getTitle())
                .content(savePostDto.getContent())
                .codeBeforeUpdate(savePostDto.getCodeBeforeUpdate())
                .codeAfterUpdate(savePostDto.getCodeAfterUpdate())
                .reference(savePostDto.getReference())
                .postImg(savePostDto.getPostImg())
                .privacy(savePostDto.getPrivacy())
                .member(member)
                .build( );

        List<String> tagList = savePostDto.getTagDtoList();
        List<TagDto> tagDtoList = new ArrayList<>(  );
        for(String name : tagList) {
            Tag tag = Tag.builder( )
                    .name(name)
                    .post(post)
                    .member(member)
                    .build( );

            tagDtoList.add(TagDto.toTagDto(tag,post));
            post.getTagList().add(tag);

        }

        postRepository.save(post);


        // 태그 한번에 저장

        return PostDto.PostResponseDto.toPostResponseDto(post,tagDtoList);

    }

    // 포스트 특정 조건으로 전체 조회
    // keyword 포함하는거 또는 memberId 포함하는 것만 조회 가능

    public Page<PostDto.PostResponseDto> postList(String keyword, Pageable pageable, Long memberId) {
        return searchPostRepository.findPostByKeyword(keyword,pageable,memberId);
    }

    public Page<PostDto.PostResponseDto> postListByTag(Long tagId,Pageable pageable) {

        return searchPostRepository.findPostByTag(tagId,pageable);

    }


    public PostDto.PostResponseDto findOne(Long postId) {

        return searchPostRepository.findOnePost(postId);

    }
}
