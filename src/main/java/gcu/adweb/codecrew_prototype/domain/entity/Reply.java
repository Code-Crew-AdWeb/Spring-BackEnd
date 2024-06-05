package gcu.adweb.codecrew_prototype.domain.entity;

import gcu.adweb.codecrew_prototype.domain.dto.ReplyDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Reply {

    @Id @GeneratedValue
    @Column(name = "reply_id")
    private Long id;

    private String content;

    private LocalDateTime date;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;


    @Builder
    public Reply(Long id, String content, Member member, Post post, LocalDateTime date) {
        this.id = id;
        this.content = content;
        this.member = member;
        this.post = post;
        this.date = date;
    }

    public static Reply toReply(ReplyDto.ReplySaveRequestDto replySaveRequestDto,Post post,Member member) {

        return Reply.builder( )
                .content(replySaveRequestDto.getContent( ))
                .member(member)
                .post(post)
                .date(LocalDateTime.now()).build( );
    }

    public void change(ReplyDto.ReplySaveRequestDto replySaveRequestDto) {
        this.content = replySaveRequestDto.getContent( );
    }
}
