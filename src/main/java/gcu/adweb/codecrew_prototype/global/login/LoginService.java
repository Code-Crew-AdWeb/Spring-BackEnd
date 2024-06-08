package gcu.adweb.codecrew_prototype.global.login;

import gcu.adweb.codecrew_prototype.domain.entity.Member;
import gcu.adweb.codecrew_prototype.domain.repository.MemberRepository;
import gcu.adweb.codecrew_prototype.global.jwt.JwtToken;
import gcu.adweb.codecrew_prototype.global.jwt.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProviderProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public JwtToken signIn(String userId, String password, HttpServletResponse httpServletResponse) {

        log.info(userId);


        Optional<Member> findMember = memberRepository.findByUserId(userId);

        try{
            if(passwordEncoder.matches(password,findMember.orElseThrow(() -> new RuntimeException("올바른 아이디를 입력해주세요")).getPassword()))
            {
                password = findMember.orElseThrow().getPassword();
            }
            else {
                throw new RuntimeException("비밀번호가 틀렸습니다");
            }
            log.info("로그인 성공!");

        }catch(Exception e) {
            log.info(e.toString());
        }



        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId,password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        JwtToken jwtToken = jwtTokenProviderProvider.generateToken(authentication);

        Cookie memberIdCookie = new Cookie("memberId", findMember.orElseThrow().getId().toString());
        Cookie refreshToken = new Cookie("refreshToken", jwtToken.getRefreshToken( ));
//        refreshToken.setHttpOnly(true);
        refreshToken.setSecure(true);
        refreshToken.setMaxAge(1000 * 60 * 6);
        refreshToken.setPath("/");
        refreshToken.setAttribute("SameSite", "None");
//        memberIdCookie.setHttpOnly(true);
        memberIdCookie.setSecure(true);
        memberIdCookie.setPath("/");
        memberIdCookie.setMaxAge(1000 * 60 * 60);
        memberIdCookie.setAttribute("SameSite", "None");

        httpServletResponse.setHeader("Authorization",jwtToken.getAccessToken( ));
        httpServletResponse.addCookie(memberIdCookie);
        httpServletResponse.addCookie(refreshToken);

        return jwtToken;

    }

}
