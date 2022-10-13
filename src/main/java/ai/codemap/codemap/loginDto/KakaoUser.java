package ai.codemap.codemap.loginDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KakaoUser {
    Long id;
    String email;
    String nickname;
}
