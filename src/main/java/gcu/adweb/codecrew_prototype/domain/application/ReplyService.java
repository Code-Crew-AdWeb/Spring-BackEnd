package gcu.adweb.codecrew_prototype.domain.application;

import gcu.adweb.codecrew_prototype.domain.dto.ReplyDto;
import gcu.adweb.codecrew_prototype.domain.dto.ReplyDto.*;
import gcu.adweb.codecrew_prototype.domain.entity.Member;
import gcu.adweb.codecrew_prototype.domain.entity.Post;
import gcu.adweb.codecrew_prototype.domain.entity.Reply;
import gcu.adweb.codecrew_prototype.domain.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
//    private final FindReplyRepository findReplyRepository;

    //댓글 생성
    public ReplyGetResponseDto saveReply(Long postId,Long memberId,ReplySaveRequestDto replySaveRequestDto) {

        Post findPost = postRepository.findById(postId).orElseThrow(()->new RuntimeException());
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException());
        Reply reply = Reply.toReply(replySaveRequestDto, findPost, member);
        Reply save = replyRepository.save(reply);
        return ReplyGetResponseDto.toReplyGetResponseDto(save.getId(),member.getUsername(),save.getContent(),save.getDate());

    }

    //댓글 리스트
    public Page<ReplyGetResponseDto> findAll(Long postId, Pageable pageable){
        return replyRepository.findPostReplies(postId, pageable);
    }

    //댓글 삭제
    public String deleteReply(Long replyId){
        Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new RuntimeException());
        replyRepository.delete(reply);
        return "ok";
    }

    //댓글 수정
    public ReplyGetResponseDto updateReply(Long memberId, Long replyId, ReplySaveRequestDto replySaveRequestDto){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException());
        Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new RuntimeException());
        reply.change(replySaveRequestDto);
        return ReplyGetResponseDto.toReplyGetResponseDto(replyId, member.getUsername(), reply.getContent(), reply.getDate());
    }

}
