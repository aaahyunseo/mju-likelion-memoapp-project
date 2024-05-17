package com.example.memoapplication.interceptor;

import com.example.memoapplication.authentication.AuthenticationContext;
import com.example.memoapplication.authentication.AuthenticationExtractor;
import com.example.memoapplication.authentication.JwtTokenProvider;
import com.example.memoapplication.errorcode.ErrorCode;
import com.example.memoapplication.exception.ForbiddenException;
import com.example.memoapplication.exception.NotFoundException;
import com.example.memoapplication.model.User;
import com.example.memoapplication.repository.UserJpaRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class Interceptor implements HandlerInterceptor {

    private final UserJpaRepository userJpaRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationContext authenticationContext;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) {
        log.info("Interceptor preHandle");

//        if (!userJpaRepository.existsByEmail(request.getHeader("email"))
//                || !userJpaRepository.existsByPassword(request.getHeader("password"))) {
//            throw new ForbiddenException(ErrorCode.LOGIN_FALSE, "email과 password가 일치하지 않습니다.");
//        }
        //HttpServletReqeust에서 accessToken 추출
        String accessToken = AuthenticationExtractor.extract(request);
        //Jwt Token에서 payload 안에 userId 추출
        UUID userId = UUID.fromString(jwtTokenProvider.getPayload(accessToken));
        //추출된 userId를 통해 user 검증, 일치할 경우 해당 user 추출
        User user = findExistingUser(userId);
        //검증된 user 객체를 담는 AuthenticationContext에 user 저장
        authenticationContext.setPrincipal(user);
        return true;
    }

    private User findExistingUser(final UUID userId) {
        //user 검증
        return userJpaRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
