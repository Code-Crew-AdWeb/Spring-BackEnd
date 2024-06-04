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



        Optional<Member> findMember = memberRepository.findByUserId(userId);

        if(passwordEncoder.matches(password,findMember.orElseThrow().getPassword()))
        {
            password = findMember.orElseThrow().getPassword();
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId,password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        JwtToken jwtToken = jwtTokenProviderProvider.generateToken(authentication);

        Cookie memberIdCookie = new Cookie("memberId",findMember.orElseThrow().getId().toString());
        Cookie refreshToken = new Cookie("refreshToken", jwtToken.getRefreshToken( ));

        httpServletResponse.setHeader("Authorization",jwtToken.getAccessToken( ));
        httpServletResponse.addCookie(memberIdCookie);
        httpServletResponse.addCookie(refreshToken);

        return jwtToken;

    }

}
