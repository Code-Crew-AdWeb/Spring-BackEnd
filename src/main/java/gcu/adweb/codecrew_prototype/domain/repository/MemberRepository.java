package gcu.adweb.codecrew_prototype.domain.repository;

import gcu.adweb.codecrew_prototype.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findById(Long memberId);

    Optional<Member> findByUserId(String userId);

    Optional<Member> findByUsername(String username);

    Boolean existsByUserId(String userId);

    Boolean existsByUsername(String username);
}
