package gcu.adweb.codecrew_prototype.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post extends  BaseEntity{
    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    private String content;

    private String reference;

    private String postImg;

    private Boolean privacy;

    private String codeBeforeUpdate;

    private String lang;

    private String codeAfterUpdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Tag> tagList;

    @Builder
    public Post(Long id, String title, String content, String reference, String postImg, Boolean privacy, String codeBeforeUpdate, String lang,String codeAfterUpdate, Member member) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.reference = reference;
        this.postImg = postImg;
        this.privacy = privacy;
        this.codeBeforeUpdate = codeBeforeUpdate;
        this.codeAfterUpdate = codeAfterUpdate;
        this.member = member;
        this.lang = lang;
        this.tagList = new ArrayList<>( );
    }
}
