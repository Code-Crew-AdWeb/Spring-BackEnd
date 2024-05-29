package gcu.adweb.codecrew_prototype.application;

import gcu.adweb.codecrew_prototype.domain.Member;
import gcu.adweb.codecrew_prototype.dto.MemberDto;
import gcu.adweb.codecrew_prototype.dto.MemberDto.*;
import gcu.adweb.codecrew_prototype.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final MemberRepository memberRepository;

    public void saveUser(SignUpDto signUpDto) {

        Member member = Member.builder( )
                .userId(signUpDto.getUserId())
                .userPw(signUpDto.getUserPw( ))
                .gitUrl(signUpDto.getGitUrl( ))
                .phoneNumber(signUpDto.getPhoneNumber())
                .username(signUpDto.getUsername())
                .build( );
        memberRepository.save(member);
    }



}
