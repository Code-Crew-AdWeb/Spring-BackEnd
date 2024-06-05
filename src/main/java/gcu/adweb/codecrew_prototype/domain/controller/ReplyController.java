package gcu.adweb.codecrew_prototype.domain.controller;

import gcu.adweb.codecrew_prototype.domain.application.ReplyService;
import gcu.adweb.codecrew_prototype.domain.dto.ReplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;

    //댓글 작성
    @PostMapping("/reply-save")
    public ResponseEntity<ReplyDto.ReplyGetResponseDto> replySave(@CookieValue("memberId")String memberId,@PathVariable Long postId,
                                                                  @RequestBody ReplyDto.ReplySaveRequestDto replySaveRequestDto){
        return ResponseEntity.ok(replyService.saveReply(postId, Long.valueOf(memberId),replySaveRequestDto));
    }

    //댓글 리스트
    @GetMapping("/reply-list")
    public Page<ReplyDto.ReplyGetResponseDto> replyList(@PathVariable Long postId, Pageable pageable){
        return replyService.findAll(postId, pageable);
    }

    //수정
    @PutMapping("/replyId={replyId}/")
    public ResponseEntity<ReplyDto.ReplyGetResponseDto> updateReply(@CookieValue("memberId")String memberId,@PathVariable(value = "replyId") Long replyId,
                                                                    @RequestBody ReplyDto.ReplySaveRequestDto replySaveRequestDto){
        return ResponseEntity.ok(replyService.updateReply(replyId, Long.valueOf(memberId),replySaveRequestDto));
    }
}
