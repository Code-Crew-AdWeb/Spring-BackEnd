package gcu.adweb.codecrew_prototype.dto;

import gcu.adweb.codecrew_prototype.domain.Member;
import gcu.adweb.codecrew_prototype.domain.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

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

        private String code;

    }


    @AllArgsConstructor
    @Data
    public static class PostResponseDto {

        private String title;

        private String content;

        private Boolean privacy;

        private String code;

        private LocalDateTime createDate;


        private String postImg;

        private List<Tag> tagList;

    }
}
