package com.example.memoapplication.controller;

import com.example.memoapplication.dto.OrganizationJoinDto;
import com.example.memoapplication.dto.response.ResponseDto;
import com.example.memoapplication.service.OrganizationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    //Organization 가입
    @PostMapping
    public ResponseEntity<ResponseDto<Void>> joinOrganization(@RequestHeader("user-id") UUID userId, @RequestBody @Valid OrganizationJoinDto organizationJoinDto) {
        organizationService.joinOrganization(organizationJoinDto, userId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "Join success"), HttpStatus.CREATED);
    }

    //Organization 탈퇴
    @DeleteMapping
    public ResponseEntity<ResponseDto<
            Void>> deleteOrganization(@RequestHeader("user-id") UUID userId) {
        organizationService.deleteOrganization(userId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "Withdrawal success"), HttpStatus.OK);
    }
}
