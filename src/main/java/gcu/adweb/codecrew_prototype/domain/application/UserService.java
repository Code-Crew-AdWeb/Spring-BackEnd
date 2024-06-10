package gcu.adweb.codecrew_prototype.domain.application;

import gcu.adweb.codecrew_prototype.domain.entity.Member;
import gcu.adweb.codecrew_prototype.domain.dto.MemberDto.*;
import gcu.adweb.codecrew_prototype.domain.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //회원 가입
    public UserInfoResponseDto signUpUser(SignUpDto signUpDto) {

        if(memberRepository.existsByUserId(signUpDto.getUserId( ))) {
            throw new IllegalArgumentException( "이미 사용중인 아이디 입니다");
        }
        if(memberRepository.existsByUsername(signUpDto.getUsername())) {
            throw new IllegalArgumentException("이미 사용중인 닉네임 입니다");
        }

        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());

        List<String> roles = new ArrayList<>(  );
        roles.add("USER");

        Member member = Member.builder( )
                .userId(signUpDto.getUserId())
                .password(encodedPassword)
//                .gitUrl(signUpDto.getGitUrl( ))
//                .phoneNumber(signUpDto.getPhoneNumber())
                .roles(roles)
                .username(signUpDto.getUsername())
                .build( );

        memberRepository.save(member);

        return UserInfoResponseDto.builder( )
                .username(member.getUsername()).build( );

    }



    //유저 프로필 조회
    public UserInfoResponseDto showUserProfile(Long memberId) {
        Optional<Member> findUser = memberRepository.findById(memberId);

        return UserInfoResponseDto.builder( )
                .username(findUser.orElseThrow().getUsername()).build( );

    }



}
