package ai.codemap.codemap;

import ai.codemap.codemap.jwt.JwtAccessDeniedHandler;
import ai.codemap.codemap.jwt.JwtAuthenticationEntryPoint;
import ai.codemap.codemap.jwt.JwtSecurityConfig;
import ai.codemap.codemap.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
            TokenProvider tokenProvider,
            CorsFilter corsFilter,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//   @Override
//    public void configure(WebSecurity web) {
//        web.ignoring()
//                .antMatchers(
//                        "/code/**"
//                        ,"/favicon.ico"
//                        ,"/error"
//                );
//    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // token??? ???????????? ???????????? ????????? csrf??? disable?????????.
                .csrf().disable()

                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // enable h2-console
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // ????????? ???????????? ?????? ????????? STATELESS??? ??????
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/algorithms").permitAll()
                .antMatchers("/algorithms/{algorithm_id}").permitAll()
                .antMatchers("/algorithms/category").permitAll()
                .antMatchers("/algorithms/category/{categoryId}").permitAll()
                .antMatchers("/submissions/all").permitAll()
                .antMatchers("/submissions/user/{user_id}").permitAll()
                .antMatchers("/submissions/problem/{problem_id}").permitAll()
                .antMatchers("/submissions/id/{submission_id}").permitAll()
                .antMatchers("/submissions/contest/{contest_id}").permitAll()
                .antMatchers("/problems").permitAll()
                .antMatchers("/problems/{problem_id}").permitAll()
                .antMatchers("/problem_sets").permitAll()
                .antMatchers("/problem_sets/{problem_set_id}").permitAll()
                .antMatchers("/contests/{contest_id}").permitAll()
                .antMatchers("/contests/start").authenticated()
                .antMatchers("/contests/finish").authenticated()
                .antMatchers("/code/save").authenticated()
                .antMatchers("/code/load").authenticated()
                .antMatchers("/submit").authenticated()
                .antMatchers("/submit/submission").permitAll()
                .antMatchers("/submit/test").permitAll()
                .antMatchers("/users/signup").permitAll()
                .antMatchers("/users/signin").permitAll()
                .antMatchers("/users/password/find").permitAll()
                .antMatchers("/users/password/update").authenticated()
                .antMatchers("/ws/**").permitAll()
                .antMatchers("/admin/**").permitAll()
                .antMatchers("/users/kakao/me").permitAll()
                .antMatchers("/users/kakao/signin").permitAll()
                .antMatchers("/users/oauth/kakao/signin").permitAll()
                .antMatchers("/users/kakao/info").permitAll()
                .antMatchers("/users/oauth/kakao/info").permitAll()
                .antMatchers("/users/kakao/getId").permitAll()
                .antMatchers("/users/oauth/kakao/getId").permitAll()
                .antMatchers("/users/kakao/interlock").authenticated()
                .antMatchers("/users/solved").authenticated()
                .antMatchers("/spotboard/{problemSetId}/contest.json").permitAll()
                .antMatchers("/spotboard/{problemSetId}/runs.json").permitAll()
                .anyRequest().authenticated()

                .and()
                .apply(new JwtSecurityConfig(tokenProvider));
    }
}