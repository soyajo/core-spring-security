package com.soya.common.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {


    /**
     * 예외에 해당하는 값을 화면에 보여주도록 구현함.
     * @param request the request during which the authentication attempt occurred.
     * @param response the response.
     * @param exception the exception which was thrown to reject the authentication
     * request.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = "Invalid Username or Password";
        if (exception instanceof BadCredentialsException) {
            errorMessage = "Invalid Username or Password";
        } else if (exception instanceof InsufficientAuthenticationException) {
            errorMessage = "Invalid Secret Key";
        }

        // 에러 메세지 url 에 추가
        setDefaultFailureUrl("/login?error=true&exception=" + exception.getMessage());
        super.onAuthenticationFailure(request, response, exception);
    }
}
