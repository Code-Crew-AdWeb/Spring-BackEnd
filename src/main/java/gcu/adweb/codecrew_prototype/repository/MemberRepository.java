package gcu.adweb.codecrew_prototype.repository;

import gcu.adweb.codecrew_prototype.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findById(String id);
    boolean existsByUserId(String userId);
}
