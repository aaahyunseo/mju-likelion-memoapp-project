package com.example.memoapplication.authentication;

import com.example.memoapplication.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Setter
@Getter
@Component
@RequestScope
public class AuthenticationContext {
    //요청 전체에서 사용할 유저를 저장할 컨텍스트
    private User principal;
}
