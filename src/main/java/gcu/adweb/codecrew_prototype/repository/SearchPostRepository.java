package gcu.adweb.codecrew_prototype.repository;

import gcu.adweb.codecrew_prototype.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchPostRepository {
    Page<PostDto.PostResponseDto> postListResponseDto(String keyword, Pageable pageable);
}
