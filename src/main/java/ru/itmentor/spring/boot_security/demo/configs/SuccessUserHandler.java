package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        String jsonResponse = "{\"message\": \"Аутентификация прошла успешно\"}";
        PrintWriter writer = response.getWriter();
        writer.print(jsonResponse);
        writer.flush();
    }
}
