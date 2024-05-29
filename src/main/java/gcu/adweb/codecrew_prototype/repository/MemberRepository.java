package gcu.adweb.codecrew_prototype.repository;

import gcu.adweb.codecrew_prototype.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

}
