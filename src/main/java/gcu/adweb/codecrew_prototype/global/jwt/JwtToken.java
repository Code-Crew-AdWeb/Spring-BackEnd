package gcu.adweb.codecrew_prototype.global.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long memberId;

    @Builder
    public JwtToken(String grantType, String accessToken, String refreshToken,Long memberId) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.memberId = memberId;
    }
}
