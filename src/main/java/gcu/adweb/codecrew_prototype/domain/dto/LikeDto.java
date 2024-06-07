package gcu.adweb.codecrew_prototype.domain.dto;

import gcu.adweb.codecrew_prototype.domain.entity.Likes;
import lombok.Builder;
import lombok.Data;

public class LikeDto {

    @Data
    public static class LikeResponseDto {

        private int likeCount;

        @Builder
        public LikeResponseDto(int likeCount) {
            this.likeCount = likeCount;
        }

        public static LikeResponseDto toLikeResponseDto(Likes likes) {

            return LikeResponseDto.builder()
                    .likeCount(likes.getLikeCount()).build();

        }
    }

    @Data
    public static class LikeRequestDto{

        private int changeCount;

        @Builder
        public LikeRequestDto(int changeCount) {
            this.changeCount = changeCount;
        }

    }



}
