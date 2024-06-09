package gcu.adweb.codecrew_prototype.domain.dto;

import gcu.adweb.codecrew_prototype.domain.entity.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostDto {

    @Data
    @NoArgsConstructor
    public static class SavePostDto {


        private String title;

        private String content;

        private Boolean privacy;

        private String codeBeforeUpdate;

        private String codeAfterUpdate;

        private String reference;

        private String postImg;

        private String lang;

        private List<String> tagList;

        @Builder
        public SavePostDto(String title, String content, Boolean privacy,String lang, String codeBeforeUpdate, String codeAfterUpdate, String reference, String postImg) {
            this.title = title;
            this.content = content;
            this.privacy = privacy;
            this.codeBeforeUpdate = codeBeforeUpdate;
            this.codeAfterUpdate = codeAfterUpdate;
            this.reference = reference;
            this.postImg = postImg;
            this.lang = lang;
            this.tagList = new ArrayList<>();
        }
    }


    @Data
    @EqualsAndHashCode(of = "postId")
    @NoArgsConstructor
    public static class PostResponseDto {

        private Long id;

        private String title;

        private String content;

        private Boolean privacy;

        private String codeBeforeUpdate;

        private String codeAfterUpdate;

        private String reference;

        private LocalDateTime createDate;

        private String username;

        private String postImg;

        private String lang;

        private List<TagDto> tagList;

        @Builder
        public PostResponseDto(Long postId, String title, String content, Boolean privacy, String codeBeforeUpdate, String codeAfterUpdate, String reference, LocalDateTime createDate, String username, String postImg, List<TagDto> tagList,String lang) {
            this.id = postId;
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
            this.lang = lang;
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
                    .lang(post.getLang())
                    .tagList(tagList)
                    .build();

        }
    }


}
