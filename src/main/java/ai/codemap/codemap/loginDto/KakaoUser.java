package ai.codemap.codemap.loginDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KakaoUser {
    private Long id;
    private String email;
    private String image;
    private String nickname;
}
