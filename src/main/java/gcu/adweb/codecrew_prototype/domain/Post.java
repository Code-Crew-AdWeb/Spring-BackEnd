package gcu.adweb.codecrew_prototype.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends  BaseEntity{
    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    private String content;

    private String postImg;

    private Boolean privacy;

    private String code;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
