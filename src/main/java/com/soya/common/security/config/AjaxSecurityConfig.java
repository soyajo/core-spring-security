//package com.soya.common.security.config;
//
//import com.soya.common.security.common.AjaxLoginAuthenticationEntryPoint;
//import com.soya.common.security.filter.AjaxLoginProcessingFilter;
//import com.soya.common.security.handler.AjaxAccessDeniedHandler;
//import com.soya.common.security.handler.AjaxAuthenticationFailureHandler;
//import com.soya.common.security.handler.AjaxAuthenticationSuccessHandler;
//import com.soya.common.security.provider.AjaxAuthenticationProvider;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
////@Configuration
////@EnableWebSecurity
////@Order(1)
////@Order(0)
//public class AjaxSecurityConfig {
//
//    @Autowired
//    private AuthenticationConfiguration authenticationConfiguration;
//
//    @Bean
//    public SecurityFilterChain ajaxFilterChain(HttpSecurity http) throws Exception {
//
//        http
//                .csrf(
//                        csrf -> csrf
//                                .disable()
//                )
//                .authorizeHttpRequests(
//                        ahr -> ahr
//                                .requestMatchers("/api/**").permitAll()
//                                .requestMatchers("/api/login").permitAll()
//                                .anyRequest().authenticated()
//                )
//                .addFilterBefore(ajaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling(
//                        eh -> eh
//                                .authenticationEntryPoint(new AjaxLoginAuthenticationEntryPoint())
//                                .accessDeniedHandler(accessDeniedHandler())
//                )
//        ;
//        return http.build();
//    }
//
//    @Bean
//    public AjaxAuthenticationProvider ajaxAuthenticationProvider() {
//        return new AjaxAuthenticationProvider();
//    }
//
//    public AccessDeniedHandler accessDeniedHandler() {
//        return new AjaxAccessDeniedHandler();
//    }
//
//    @Bean
//    public AjaxLoginProcessingFilter ajaxLoginProcessingFilter() throws Exception {
//
//        AjaxLoginProcessingFilter ajaxLoginProcessingFilter = new AjaxLoginProcessingFilter();
//        ajaxLoginProcessingFilter.setAuthenticationManager(authenticationManager());
//        ajaxLoginProcessingFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
//        ajaxLoginProcessingFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
//        return ajaxLoginProcessingFilter;
//
//    }
//    @Bean
//    public AuthenticationManager authenticationManager () throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public AuthenticationSuccessHandler authenticationSuccessHandler() {
//        return new AjaxAuthenticationSuccessHandler();
//    }
//
//    @Bean
//    AjaxAuthenticationFailureHandler authenticationFailureHandler() {
//        return new AjaxAuthenticationFailureHandler();
//    }
//}
