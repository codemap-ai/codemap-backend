package ai.codemap.codemap.service;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.apache.commons.codec.binary.Base64;
import ai.codemap.codemap.KakaoOAuth2;
import ai.codemap.codemap.exception.DuplicateMemberException;
import ai.codemap.codemap.loginDto.KakaoUser;
import ai.codemap.codemap.loginDto.UserDto;
import ai.codemap.codemap.model.Authority;
import ai.codemap.codemap.model.User;
import ai.codemap.codemap.repository.UserRepository;

import ai.codemap.codemap.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.basic.BasicEditorPaneUI;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KakaoOAuth2 kakaoOAuth2;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, KakaoOAuth2 kakaoOAuth2) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.kakaoOAuth2 = kakaoOAuth2;
    }


    public UserDto signup(UserDto userDto) throws Exception{
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();


        byte[] encodedPasswordBytes = userDto.getPassword().getBytes(Charset.forName("ASCII"));
        String base64EncodedPassword = base64Encode(encodedPasswordBytes);


        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .encPassword(base64EncodedPassword)
                .nickname(userDto.getNickname())
                .email(userDto.getEmail())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .socialId("")
                .build();


        return UserDto.from(userRepository.save(user));
    }

    public KakaoUser kakaoUserInfo(String code, String endPoint){
        KakaoUser kakaoUser = kakaoOAuth2.getUserInfo(code, endPoint);
        System.out.println(kakaoUser);
        return kakaoUser;
    }
    public User kakaoSignin(String code, String endPoint){
        KakaoUser kakaoUser = kakaoOAuth2.getUserInfo(code, endPoint);
        System.out.println(kakaoUser);
        String social_id = "kakao" + kakaoUser.getId();
        User user = userRepository.findBySocialId(social_id);
        return user;
    }
    public UserDto kakaoInterlock(Long id) {
        User user = getCurrentUser();
        System.out.println("user");
        System.out.println(user);
        String social_id = "kakao" + id.toString();
        user.setSocialId(social_id);

        return UserDto.from(userRepository.save(user));
    }

    public UserDto getUserWithAuthorities(String username) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername).orElse(null));
    }

    public Long getCurrentUserId() {
        return userRepository.getIdByUsername(SecurityUtil.getCurrentUsername().get());
    }

    public User getCurrentUser() {
        return userRepository.findByUserId(getCurrentUserId());
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserByUserId(Long userId) {
        return userRepository.findByUserId(userId);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }
    public User getUserBySocialId(String socialId) {return userRepository.findBySocialId(socialId);}
    public static String base64Encode(byte[] bytes) {
        return (new Base64()).encodeToString(bytes);
    }


}
