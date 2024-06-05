package gcu.adweb.codecrew_prototype.domain.dto;

import gcu.adweb.codecrew_prototype.domain.entity.Post;
import gcu.adweb.codecrew_prototype.domain.entity.Tag;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

@Data
@NoArgsConstructor
@Getter
public class TagDto {

    @JsonIgnore
    private Long id;

    private String name;

    private Long postId;

    @Builder
    public TagDto(Long tagId, String name, Long postId) {
        this.id = tagId;
        this.name = name;
        this.postId= postId;
    }

    public static TagDto toTagDto(Tag tag) {

        return TagDto.builder()
                .tagId(tag.getId())
                .name(tag.getName())
                .postId(tag.getPost().getId()).build();

    }

}
