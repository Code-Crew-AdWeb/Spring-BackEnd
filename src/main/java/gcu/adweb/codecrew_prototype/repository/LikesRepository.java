package gcu.adweb.codecrew_prototype.repository;

import gcu.adweb.codecrew_prototype.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes,Long> {
}
