package gcu.adweb.codecrew_prototype.controller;

import gcu.adweb.codecrew_prototype.application.UserService;
import gcu.adweb.codecrew_prototype.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberDto.SignUpDto signUpDto) {
        try {
            userService.saveUser(signUpDto);
            return ResponseEntity.ok("User signed up successfully");
        }
        // ID 가 중복될 경우 에러 메시지를 반환한다
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDto.LoginDto loginDto) {
        try {
            userService.loginUser(loginDto);
            return (ResponseEntity<?>) ResponseEntity.ok();
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body((e.getMessage()));
        }
    }
}
