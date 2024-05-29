package gcu.adweb.codecrew_prototype.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.EnableMBeanExport;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tag {

    @Id @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
