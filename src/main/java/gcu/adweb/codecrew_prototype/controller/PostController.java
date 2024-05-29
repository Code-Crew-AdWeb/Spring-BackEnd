package gcu.adweb.codecrew_prototype.controller;


import gcu.adweb.codecrew_prototype.application.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class PostController {

    private final PostService postService;


}
