package gcu.adweb.codecrew_prototype.domain.entity;

import gcu.adweb.codecrew_prototype.domain.dto.LikeDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Likes {

    @Id @GeneratedValue
    @Column(name = "likes_id")
    private Long id;

    private int likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Likes(Long id, int likeCount, Member member, Post post) {
        this.id = id;
        this.likeCount = likeCount;
        this.member = member;
        this.post = post;
    }

    public static Likes toLikes(Member member,Post post) {

        return Likes.builder( )
                .likeCount(0)
                .member(member)
                .post(post).build( );

    }

    public void update(LikeDto.LikeRequestDto likeRequestDto) {

        this.likeCount = this.likeCount + likeRequestDto.getChangeCount();

    }
}
