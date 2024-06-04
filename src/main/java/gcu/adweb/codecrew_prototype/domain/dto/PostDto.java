package gcu.adweb.codecrew_prototype.domain.dto;

import gcu.adweb.codecrew_prototype.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class PostDto {

    @AllArgsConstructor
    @Data
    public static class SavePostDto {


        private String title;

        private String content;

        private Boolean privacy;

        private String codeBeforeUpdate;

        private String codeAfterUpdate;

        private String reference;

        private String postImg;

        private List<String> tagDtoList;

    }


    @Data
    @Builder
    @EqualsAndHashCode(of = "postId")
    public static class PostResponseDto {

        private Long postId;

        private String title;

        private String content;

        private Boolean privacy;

        private String codeBeforeUpdate;

        private String codeAfterUpdate;

        private String reference;

        private LocalDateTime createDate;

        private String username;

        private String postImg;

        private List<TagDto> tagList;

        @Builder
        public PostResponseDto(Long postId, String title, String content, Boolean privacy, String codeBeforeUpdate, String codeAfterUpdate, String reference, LocalDateTime createDate, String username, String postImg, List<TagDto> tagList) {
            this.postId = postId;
            this.title = title;
            this.content = content;
            this.privacy = privacy;
            this.codeBeforeUpdate = codeBeforeUpdate;
            this.codeAfterUpdate = codeAfterUpdate;
            this.reference = reference;
            this.createDate = createDate;
            this.username = username;
            this.postImg = postImg;
            this.tagList = tagList;
        }
        public static PostResponseDto toPostResponseDto(Post post, List<TagDto> tagList) {

            return PostResponseDto.builder()
                    .postId(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .privacy(post.getPrivacy())
                    .codeBeforeUpdate(post.getCodeBeforeUpdate())
                    .codeAfterUpdate(post.getCodeAfterUpdate())
                    .reference(post.getReference())
                    .createDate(post.getCreatedDate())
                    .username(post.getMember().getUsername())
                    .postImg(post.getPostImg())
                    .tagList(tagList)
                    .build();

        }
    }


}
