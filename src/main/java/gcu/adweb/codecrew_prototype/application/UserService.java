package gcu.adweb.codecrew_prototype.application;

import gcu.adweb.codecrew_prototype.domain.Member;
import gcu.adweb.codecrew_prototype.dto.MemberDto;
import gcu.adweb.codecrew_prototype.dto.MemberDto.*;
import gcu.adweb.codecrew_prototype.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final MemberRepository memberRepository;

    // 회원 가입
    public void saveUser(SignUpDto signUpDto) {
        // ID 중복 시 에러를 던진다
        if (memberRepository.existsByUserId(signUpDto.getUserId())) {
            throw new RuntimeException("User ID already exists.");
        }
        Member member = Member.builder()
                .userId(signUpDto.getUserId())
                .userPw(signUpDto.getUserPw())
                .gitUrl(signUpDto.getGitUrl())
                .phoneNumber(signUpDto.getPhoneNumber())
                .username(signUpDto.getUsername())
                .build();
        memberRepository.save(member);
    }
    public void loginUser(LoginDto loginDto) {
        Optional<Member> optionalMember = memberRepository.findById(loginDto.getUserId());
        String errorMessage = "Your account information is incorrect, please check again";

        // 입력한 ID 가 DB 에 존재하지 않거나, ID 에 해당하는 Password 가 일치하지 않을 경우 에러를 던진다
        if (optionalMember.isEmpty() || !optionalMember.get().getUserPw().equals(loginDto.getUserPw())) {
            throw new RuntimeException(errorMessage);
        }
    }

    //유저 프로필 조회
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }


}
