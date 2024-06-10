package gcu.adweb.codecrew_prototype.global.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public class SignInDto {
    private String userId;
    private String password;

    @Builder
    public SignInDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
