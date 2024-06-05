package gcu.adweb.codecrew_prototype.domain.controller;


import gcu.adweb.codecrew_prototype.domain.application.PostService;
import gcu.adweb.codecrew_prototype.domain.dto.PostDto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    // 포스트 저장
    @PostMapping("/save")
    public SavePostDto savePost(@RequestBody SavePostDto savePostDto,@CookieValue("memberId") Long memberId ) {

        postService.savePost(savePostDto,memberId);

        return savePostDto;
    }

    // 포스트 검색어로 불러오기
    @GetMapping("/list/search")
    public Page<PostResponseDto> postList(@CookieValue String memberId , @RequestParam(required = false) String keyword, Pageable pageable) {


        log.info("memberId={}", memberId);
        Page<PostResponseDto> postResponseDtos = postService.postList(keyword, pageable, null);

        return postResponseDtos;

    }

    // 자기 포스트 불러오기
    @GetMapping("/list/my-list")
    public Page<PostResponseDto> myPostList(@CookieValue String memberId , @RequestParam(required = false) String keyword, Pageable pageable) {

        log.info("memberId={}", memberId);
        Page<PostResponseDto> postResponseDtos = postService.postList(keyword, pageable, Long.valueOf(memberId));

        return postResponseDtos;

    }

//    @GetMapping("/list")
//    public Page<PostResponseDto> postList(HttpServletRequest request, @RequestParam(required = false) String keyword, Pageable pageable) {
//
//        Cookie[] cookies = request.getCookies();
//        String memberId = null;
//        if(cookies != null){
//            for (Cookie cookie : cookies) {
//                if(cookie.getName().equals("memberId")){
//                    memberId = cookie.getValue();
//                }
//            }
//        }
//
//        log.info("memberId={}", memberId);
//        Page<PostResponseDto> postResponseDtos = postService.postList(keyword, pageable, Long.valueOf(memberId));
//
//        return postResponseDtos;
//
//    }

    // 포스트 하나 조회
    @GetMapping("/list/post-id={postId}")
    public PostResponseDto post(@PathVariable Long postId) {
        PostResponseDto postResponseDto = postService.findOne(postId);

        return postResponseDto;
    }

    // 특정 태그에 관한 포스트 조회
    @GetMapping("/list/tag-id={tagId}")
    public Page<PostResponseDto> findPostByTag(@PathVariable Long tagId, Pageable pageable) {

        return postService.postListByTag(tagId,pageable);

    }



}
