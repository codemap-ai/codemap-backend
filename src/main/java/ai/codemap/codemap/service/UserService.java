package ai.codemap.codemap.service;

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

import java.util.Collections;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KakaoOAuth2 kakaoOAuth2;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, KakaoOAuth2 kakaoOAuth2) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.kakaoOAuth2 = kakaoOAuth2;
    }


    @Transactional
    public UserDto signup(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .email(userDto.getEmail())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .socialId(-1L)
                .build();


        return UserDto.from(userRepository.save(user));
    }

    @Transactional
    public UserDto kakaoSignin(String code) {

        System.out.println(code);
        KakaoUser kakaoUser = kakaoOAuth2.getUserInfo(code);
        String username = "KAKAO" + kakaoUser.getId();
        if (userRepository.findOneWithAuthoritiesByUsername(username).orElse(null) != null) {
            return UserDto.from(getUserByUsername(username));
        }

        Authority authority = Authority.builder()
                        .authorityName("ROLE_USER")
                        .build();

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(kakaoUser.getEmail()))
                .nickname(kakaoUser.getNickname())
                .email(kakaoUser.getEmail())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .socialId(kakaoUser.getId())
                .build();

        return UserDto.from(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String username) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    @Transactional(readOnly = true)
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


}
