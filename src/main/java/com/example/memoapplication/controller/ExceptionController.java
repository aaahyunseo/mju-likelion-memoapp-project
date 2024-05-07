package com.example.memoapplication.controller;

import com.example.memoapplication.dto.response.ErrorResponseDto;
import com.example.memoapplication.errorcode.ErrorCode;
import com.example.memoapplication.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    //UserNotFoundException 예외처리 핸들러
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handlerUserNotFoundException(UserNotFoundException userNotFoundException) {
        return new ResponseEntity<>(ErrorResponseDto.res(userNotFoundException), HttpStatus.NOT_FOUND);
    }

    //MemoNotFoundException 예외처리 핸들러
    @ExceptionHandler(MemoNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleMemoNotFoundException(
            MemoNotFoundException memoNotFoundException) {
        return new ResponseEntity<>(ErrorResponseDto.res(memoNotFoundException), HttpStatus.NOT_FOUND);
    }

    //AlreadyExistException 예외처리 핸들러
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ErrorResponseDto> handleAlreadyExistException(
            AlreadyExistException alreadyExistException) {
        return new ResponseEntity<>(ErrorResponseDto.res(alreadyExistException), HttpStatus.CONFLICT);
    }

    //AlreadyExistException 예외처리 핸들러
    @ExceptionHandler(LoginFalseException.class)
    public ResponseEntity<ErrorResponseDto> handleLoginFalseException(
            LoginFalseException loginFalseException) {
        return new ResponseEntity<>(ErrorResponseDto.res(loginFalseException), HttpStatus.UNAUTHORIZED);
    }

    // 원인을 알 수 없는 예외 처리
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception exception) {
        return new ResponseEntity<>(
                ErrorResponseDto.res(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), exception),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException methodArgumentNotValidException) {
        FieldError fieldError = methodArgumentNotValidException.getBindingResult().getFieldError();
        if (fieldError == null) {
            return new ResponseEntity<>(ErrorResponseDto.res(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    methodArgumentNotValidException), HttpStatus.BAD_REQUEST);
        }
        ErrorCode validationErrorCode = ErrorCode.resolveValidationErrorCode(fieldError.getCode());
        String detail = fieldError.getDefaultMessage();
        DtoValidationException dtoValidationException = new DtoValidationException(validationErrorCode, detail);

        return new ResponseEntity<>(ErrorResponseDto.res(dtoValidationException), HttpStatus.BAD_REQUEST);
    }
}
