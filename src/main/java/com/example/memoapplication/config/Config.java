package com.example.memoapplication.config;

import com.example.memoapplication.authentication.AuthenticatedUserArgumentResolver;
import com.example.memoapplication.interceptor.Interceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class Config implements WebMvcConfigurer {

    private final AuthenticatedUserArgumentResolver authenticatedUserArgumentResolver;
    private final Interceptor interceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        //인터셉터 등록
        registry.addInterceptor(interceptor)
                .addPathPatterns("/users/**", "/memos/**", "/organizations/**")
                .excludePathPatterns("/users/login");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        //어노테이션 등록
        resolvers.add(authenticatedUserArgumentResolver);
    }

}
