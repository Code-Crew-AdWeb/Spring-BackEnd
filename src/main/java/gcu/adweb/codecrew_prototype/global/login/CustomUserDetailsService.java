package gcu.adweb.codecrew_prototype.global.login;

import gcu.adweb.codecrew_prototype.domain.entity.Member;
import gcu.adweb.codecrew_prototype.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return memberRepository.findByUserId(userId)
                .map(this::createUserDetails)
                .orElseThrow(()->new UsernameNotFoundException("해당하는 회원을 찾을수 없습니다"));
    }

    private UserDetails createUserDetails(Member member) {
        return User.builder( )
                .username(member.getUserId( ))
                .password(passwordEncoder.encode(member.getPassword( )))
                .roles(member.getRoles().toArray( new String[0]))
                .build();
    }
}
