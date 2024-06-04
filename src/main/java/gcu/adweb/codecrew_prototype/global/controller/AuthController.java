package gcu.adweb.codecrew_prototype.global.controller;

import gcu.adweb.codecrew_prototype.global.dto.SignInDto;
import gcu.adweb.codecrew_prototype.global.jwt.JwtToken;
import gcu.adweb.codecrew_prototype.global.login.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class AuthController {

    private final LoginService loginService;

    @PostMapping("/sign-in")
    public JwtToken signIn(@RequestBody SignInDto signInDto, HttpServletResponse httpServletResponse) {
        String userId = signInDto.getUserId();
        String password = signInDto.getPassword();

//        String encodePassword = passwordEncoder.encode(password);
//        log.info(encodePassword);
        JwtToken jwtToken = loginService.signIn(userId,password,httpServletResponse);
        log.info("request userId = {}, password = {}", userId, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());



        return jwtToken;
    }

}
