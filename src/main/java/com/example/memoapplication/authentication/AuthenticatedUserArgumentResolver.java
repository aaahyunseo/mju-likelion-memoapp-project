package com.example.memoapplication.authentication;

import com.example.memoapplication.annotation.AuthenticatedUser;
import com.example.memoapplication.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthenticatedUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final AuthenticationContext authenticationContext;

    //어떤 어노테이션의 기능인지
    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticatedUser.class);
    }

    //해당 어노테이션의 실제 동작 정의 - 검증된 User 객체를 반환
    @Override
    public User resolveArgument(final MethodParameter parameter,
                                final ModelAndViewContainer mavContainer,
                                final NativeWebRequest webRequest,
                                final WebDataBinderFactory binderFactory) {
        return authenticationContext.getPrincipal();
    }
}
