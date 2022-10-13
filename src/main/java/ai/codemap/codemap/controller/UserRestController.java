package ai.codemap.codemap.controller;

import ai.codemap.codemap.jwt.JwtFilter;
import ai.codemap.codemap.jwt.TokenProvider;
import ai.codemap.codemap.loginDto.*;
import ai.codemap.codemap.model.User;
import ai.codemap.codemap.service.UserService;
import com.mysql.cj.log.Log;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserRestController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    public UserRestController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserService userService, PasswordEncoder passwordEncoder, JavaMailSender javaMailSender) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@Valid @RequestBody UserDto userDto) {
        if (userDto.getUsername().length() >= 5 && userDto.getUsername().substring(0, 5).equals("KAKAO"))
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(userService.signup(userDto));
    }

    @PostMapping("/password/find")
    public ResponseEntity findPassword(@Valid @RequestBody findPasswordDto findPasswordDto) throws MessagingException, UnsupportedEncodingException {

        User user = userService.getUserByUsername(findPasswordDto.getUsername());

        if (user == null || !Objects.equals(user.getEmail(), findPasswordDto.getEmail())) {
            return ResponseEntity.badRequest().build();
        }

        String to = user.getEmail();
        String from = "ai.codemap@gmail.com";
        String subject = "코드맵 임시 비밀번호 발급";
        String newPassword = RandomStringUtils.randomAlphanumeric(10);
        user.setPassword(passwordEncoder.encode(newPassword));

        userService.addUser(user);

        StringBuilder body = new StringBuilder();
        body.append("<html> <body><h1>안녕하세요 코드맵입니다. </h1>");
        body.append("<html> <body><h3>귀하의 임시 비밀번호를 발급해드립니다. </h3>");
        body.append("<div> 새로운 임시 비밀번호는 ");
        body.append(newPassword);
        body.append(" 입니다.");
        body.append(" 감사합니다.</div> </body></html>");

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");

        mimeMessageHelper.setFrom(from, "Codemap");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body.toString(), true);

        javaMailSender.send(message);
        return ResponseEntity.ok(findPasswordDto);
    }

    @PostMapping("/password/update")
    public ResponseEntity updatePassword(@Valid @RequestBody updatePasswordDto updatePasswordDto) {

        User user = userService.getUserByUserId(userService.getCurrentUserId());

        user.setPassword(passwordEncoder.encode(updatePasswordDto.getPassword()));
        userService.addUser(user);

        return ResponseEntity.ok(UserDto.from(user));
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity getMyUserInfo(HttpServletRequest request) {

        return ResponseEntity.ok(userService.getMyUserWithAuthorities());
    }

    @PostMapping("/signin")
    public ResponseEntity authorize(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/oauth/kakao/signin")
    public String authorizeWithKakao() {
        return "redirect:https://kauth.kakao.com/oauth/authorize?client_id=f796398f8dc1c3d64a37a9e053a9be9b&redirect_uri=https://api.codemap.ai/users/kakao/signin&response_type=code";
    }

    @GetMapping("/kakao/signin")
    public ResponseEntity KakaoLogin(String code) {

        UserDto userDto = userService.kakaoSignin(code);

        LoginDto loginDto = LoginDto.builder()
                .username(userDto.getUsername())
                .password(userDto.getEmail())
                .build();

        return authorize(loginDto);
    }
}
