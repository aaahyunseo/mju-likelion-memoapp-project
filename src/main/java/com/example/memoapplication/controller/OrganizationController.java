package com.example.memoapplication.controller;

import com.example.memoapplication.dto.request.OrganizationCreateDto;
import com.example.memoapplication.dto.request.OrganizationJoinDto;
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

    //Organization 생성
    @PostMapping
    public ResponseEntity<ResponseDto<Void>> createOrganization(@RequestBody @Valid OrganizationCreateDto organizationCreateDto) {
        organizationService.createOrganization(organizationCreateDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "Create success"), HttpStatus.CREATED);
    }

    //Organization 가입
    @PostMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> joinOrganization(@RequestHeader("user-id") UUID userId, @PathVariable UUID id, @RequestBody @Valid OrganizationJoinDto organizationJoinDto) {
        organizationService.joinOrganization(organizationJoinDto, userId, id);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "Join success"), HttpStatus.CREATED);
    }

    //Organization 탈퇴
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<
            Void>> deleteOrganization(@RequestHeader("user-id") UUID userId, @PathVariable UUID id) {
        organizationService.deleteOrganization(userId, id);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "Withdrawal success"), HttpStatus.OK);
    }
}
