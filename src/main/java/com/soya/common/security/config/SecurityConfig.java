package com.soya.common.security.config;


import com.soya.common.security.common.FormWebAuthenticationDetails;
import com.soya.common.security.common.FromAuthenticationDetailsSource;
import com.soya.common.security.filter.AjaxLoginProcessingFilter;
import com.soya.common.security.handler.CustomAccessDeniedHandler;
import com.soya.common.security.handler.CustomAuthenticationSuccessHandler;
import com.soya.common.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import com.soya.common.security.provider.CustomAuthenticationProvider;
import com.soya.common.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Order(0)
public class SecurityConfig {

    @Autowired
    private FromAuthenticationDetailsSource fromAuthenticationDetailsSource;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
//                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests((authorizeRequest) -> authorizeRequest
                        .requestMatchers("/api").permitAll()
                        .requestMatchers("/users").permitAll()
                        .requestMatchers("/user/login/**").permitAll()
                        .requestMatchers("/login*").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/mypage").hasRole("USER")
                        .requestMatchers("/messages").hasRole("MANAGER")
                        .requestMatchers("/mypage").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(
                        (formLogin) -> formLogin
                                .defaultSuccessUrl("/")
                                .loginPage("/login")
                                .loginProcessingUrl("/login_proc")
                                .successHandler(authenticationSuccessHandler)
                                .failureHandler(authenticationFailureHandler)
                                .authenticationDetailsSource(fromAuthenticationDetailsSource)
                                .permitAll()
                ).logout(
                        (logout) -> logout
                                .logoutUrl("/logout")
                ).exceptionHandling(
                        (exceptionHandling) -> exceptionHandling
                                .accessDeniedHandler(accessDeniedHandler())
                )
        ;
//                .rememberMe(    // 아이디 기억하기 버튼 기능
//                        (rememberMe) -> rememberMe
//                                .rememberMeParameter("remember") // 기본 파라미터명은 remember-me
//                                .tokenValiditySeconds(3600) //default 는 14일
//                                .alwaysRemember(true) // 리멤버 미 기능이 활성화되지 않아도 항상 실행
//                                .userDetailsService(userDetailsService)
//                );
        return http.build();
    }

    // 비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // 아이디 및 비밀번호 등 검증
    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(passwordEncoder());
    }

    // 권한 없는 사용자 핸들링
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        CustomAccessDeniedHandler accessDeniedHandler = new CustomAccessDeniedHandler();
        accessDeniedHandler.setErrorPage("/denied");
        return accessDeniedHandler;
    }


    // 정적자원관리 js, css ...  보안 필터 해제하기
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

//    @Bean
//    public FilterSecurityInterceptor customFilterSecurityInterceptor() {
//        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
//        filterSecurityInterceptor.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource());
//        filterSecurityInterceptor.setAccessDecisionManager(affirmativeBased());
//        filterSecurityInterceptor.setAuthenticationManager(authenticationManagerBean());
//    }
}
