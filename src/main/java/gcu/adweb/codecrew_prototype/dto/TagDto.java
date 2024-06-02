package gcu.adweb.codecrew_prototype.dto;

import gcu.adweb.codecrew_prototype.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

@Data
public class TagDto {

    @JsonIgnore
    private Long tagId;

    private String name;

    @Builder
    public TagDto(Long tagId, String name) {
        this.tagId = tagId;
        this.name = name;
    }

    public static TagDto toTagDto(Tag tag, String name) {

        return TagDto.builder()
                .tagId(tag.getId())
                .name(name).build();

    }

}
