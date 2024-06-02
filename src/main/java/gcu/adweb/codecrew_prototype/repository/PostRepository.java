package gcu.adweb.codecrew_prototype.repository;

import gcu.adweb.codecrew_prototype.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {

        Optional<Post> findById(Long postId);

}
