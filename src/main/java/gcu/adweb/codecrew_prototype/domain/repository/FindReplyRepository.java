package gcu.adweb.codecrew_prototype.domain.repository;

import gcu.adweb.codecrew_prototype.domain.dto.ReplyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindReplyRepository {
    Page<ReplyDto.ReplyGetResponseDto> findPostReplies(Long postId, Pageable pageable);

}
