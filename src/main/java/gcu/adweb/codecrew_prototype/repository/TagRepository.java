package gcu.adweb.codecrew_prototype.repository;

import gcu.adweb.codecrew_prototype.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Long> {
}
