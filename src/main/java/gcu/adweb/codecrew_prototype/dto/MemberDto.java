package gcu.adweb.codecrew_prototype.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class MemberDto {

    @AllArgsConstructor
    @Data
    public static class SignUpDto {

        private String username;

        private String phoneNumber;

        private String userId;

        private String userPw;

        private String gitUrl;

        private String profileImageUrl;

    }


}
