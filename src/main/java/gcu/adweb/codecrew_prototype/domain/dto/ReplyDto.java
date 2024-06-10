package gcu.adweb.codecrew_prototype.domain.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReplyDto {

    @Getter
    @NoArgsConstructor
    public static class ReplyGetResponseDto{

        private Long replyId;
        private String username;
        private String content;
        private String replyingTime;

        @Builder
        public ReplyGetResponseDto(Long replyId, String username, String content, String replyingTime) {
            this.replyId = replyId;
            this.username = username;
            this.content = content;
            this.replyingTime = replyingTime;
        }

        public static ReplyGetResponseDto toReplyGetResponseDto(Long replyId, String username, String content, LocalDateTime replyTime) {
            return ReplyGetResponseDto.builder()
                    .replyId(replyId)
                    .username(username)
                    .content(content)
                    .replyingTime(replyTime.toString()).build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ReplySaveRequestDto{

        private String content;

        @Builder
        public ReplySaveRequestDto(String content) {
            this.content = content;
        }
    }


}
