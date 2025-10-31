package com.anjos_bolos.anjos_bolos_api.infrastructure.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CustomAcessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public CustomAcessDeniedHandler() {
        this.objectMapper = new ObjectMapper();
    }


    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = "SEM_ACESSO";

        if (auth != null && auth.getAuthorities() != null && !auth.getAuthorities().isEmpty()) {
            GrantedAuthority ga = auth.getAuthorities().iterator().next();
            String authority = ga != null ? ga.getAuthority() : null;

            if (authority != null && authority.startsWith("ROLE_")) {
                role = authority.substring(5);
            }
            else if (authority != null) {
                role = authority;
            }
        }

        int status = HttpServletResponse.SC_UNAUTHORIZED;
        String error = "Unauthorized";
        String message = String.format("Acesso negado para nível(s) de acesso: %s.",role);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", OffsetDateTime.now().toString());
        body.put("status", status);
        body.put("error", error);
        body.put("message", message);
        body.put("path", request.getRequestURI());

        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        objectMapper.writeValue(response.getWriter(), body);
    }
}