package gcu.adweb.codecrew_prototype.domain.repository;

import gcu.adweb.codecrew_prototype.domain.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes,Long> {
}
