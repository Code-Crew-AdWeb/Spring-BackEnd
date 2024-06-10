package gcu.adweb.codecrew_prototype.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class MemberDto {


    @Data
    public static class SignUpDto {

        private String username;

//        private String phoneNumber;

        private String userId;

        private String password;

//        private String gitUrl;

//        private String profileImageUrl;


        @Builder
        public SignUpDto(String username, String userId, String password) {
            this.username = username;
            this.userId = userId;
            this.password = password;
        }


    }

    @Data
    public static class UserInfoResponseDto {

        private String username;

        @Builder
        public UserInfoResponseDto(String username) {
            this.username = username;
        }
    }




}
