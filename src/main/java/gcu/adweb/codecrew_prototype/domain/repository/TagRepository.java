package gcu.adweb.codecrew_prototype.domain.repository;

import gcu.adweb.codecrew_prototype.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Long> {
}
