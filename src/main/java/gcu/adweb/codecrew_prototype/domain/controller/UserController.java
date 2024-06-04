package gcu.adweb.codecrew_prototype.domain.controller;

import gcu.adweb.codecrew_prototype.domain.application.UserService;
import gcu.adweb.codecrew_prototype.domain.dto.MemberDto;
import gcu.adweb.codecrew_prototype.domain.dto.MemberDto.*;
import gcu.adweb.codecrew_prototype.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    // 유저 프로필 조회
    @GetMapping("/profile")
    public UserInfoResponseDto showUserProfile(@CookieValue("memberId") Long memberId ) {


        return userService.showUserProfile(memberId);

    }

    // 유저 회원가입

    @PostMapping("/sign-up")
    public UserInfoResponseDto signUpUser( @RequestBody  SignUpDto signUpDto) {

        return userService.signUpUser(signUpDto);

    }


}
