package com.example.online_shop_back.security;


import com.example.online_shop_back.entity.User;
import com.example.online_shop_back.repository.UserRepository;
import com.example.online_shop_back.utils.AppConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest httpServletRequest,
                                    @NotNull HttpServletResponse httpServletResponse,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        log.info("httpServletRequest {}", httpServletRequest);

        try {
            setUserPrincipalIfAllOk(httpServletRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void setUserPrincipalIfAllOk(HttpServletRequest request) {
        String authorization = request.getHeader(AppConstant.AUTHORIZATION_HEADER);

        log.info("authorization {}", authorization);

        if (authorization != null) {
            User user = null;
            if (authorization.startsWith("Bearer ")) {
                user = getUserFromBearerToken(authorization);
            } else if (authorization.startsWith("Basic ")) {
                user = getUserFromBasicToken(authorization);
            }
            if (user != null) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
    }

    public User getUserFromBasicToken(String token) {
        log.info("token {}", token);
        token = token.substring("Basic".length()).trim();
        log.info("token {}", token);
        byte[] decode = Base64.getDecoder().decode(token);
        token = new String(decode, Charset.defaultCharset());
        String[] split = token.split(":", 2);
        log.info("token {}", token);
        log.info("split[0] {}", split[0]);

        Optional<User> optionalUser = userRepository.findFirstByPhoneNumberAndEnabledIsTrueAndAccountNonExpiredIsTrueAndCredentialsNonExpiredIsTrueAndAccountNonLockedIsTrue(split[0]);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            log.info("user {}", user);
            if (passwordEncoder.matches(split[1], user.getPassword()))
                return optionalUser.get();
        }
        return null;
    }

    public User getUserFromBearerToken(String token) {
        try {

            token = token.substring("Bearer".length()).trim();
            if (jwtTokenProvider.validToken(token, true)) {
                String userId = jwtTokenProvider.getUserIdFromToken(token, true);

                return userRepository.findById(UUID.fromString(userId)).orElse(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
