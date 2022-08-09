package ai.codemap.codemap.service;

import ai.codemap.codemap.exception.DuplicateMemberException;
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

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

    public Long getCurrentUserId(){
        return userRepository.getIdByUsername(SecurityUtil.getCurrentUsername().get());
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public User getUserByUserId(Long userId){
        return userRepository.findByUserId(userId);
    }
    public User addUser(User user){
        return userRepository.save(user);
    }

}
