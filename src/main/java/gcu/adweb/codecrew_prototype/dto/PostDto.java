package gcu.adweb.codecrew_prototype.dto;

import gcu.adweb.codecrew_prototype.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

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
    public static class RequestPostDto {
        private String title;

        private String content;

        private Boolean privacy;

        private String code;

        private LocalDateTime createDate;
    }
}
