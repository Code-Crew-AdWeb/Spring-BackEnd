package gcu.adweb.codecrew_prototype.domain.controller;


import gcu.adweb.codecrew_prototype.domain.application.PostService;
import gcu.adweb.codecrew_prototype.domain.dto.PostDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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

    //포스트 불러오기

    @GetMapping("/list")
    public Page<PostResponseDto> postList(@CookieValue("memberId") Long memberId ,@RequestParam(required = false) String keyword, Pageable pageable) {

        Page<PostResponseDto> postResponseDtos = postService.postList(keyword, pageable, memberId);

        return postResponseDtos;

    }




}
