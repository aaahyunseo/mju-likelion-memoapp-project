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

    //NotFoundException 예외처리 핸들러
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handlerNotFoundException(NotFoundException notFoundException) {
        this.writeLog(notFoundException);
        HttpStatus httpStatus = HttpStatus.valueOf(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(ErrorResponseDto.res(notFoundException), httpStatus);
    }


    //AlreadyExistException 예외처리 핸들러
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ErrorResponseDto> handleAlreadyExistException(
            AlreadyExistException alreadyExistException) {
        this.writeLog(alreadyExistException);
        HttpStatus httpStatus = HttpStatus.valueOf(ErrorCode.ALREADY_EXIST.getCode());
        return new ResponseEntity<>(ErrorResponseDto.res(alreadyExistException), httpStatus);
    }

    //LoginFalseException 예외처리 핸들러
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponseDto> handleLoginFalseException(
            ForbiddenException forbiddenException) {
        this.writeLog(forbiddenException);
        HttpStatus httpStatus = HttpStatus.valueOf(ErrorCode.LOGIN_FALSE.getCode());
        return new ResponseEntity<>(ErrorResponseDto.res(forbiddenException), httpStatus);
    }


    //InvalidTokenException 예외처리 핸들러
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponseDto> handleLoginFalseException(
            InvalidTokenException invalidTokenException) {
        this.writeLog(invalidTokenException);
        HttpStatus httpStatus = HttpStatus.valueOf(ErrorCode.TOKEN_INVALID.getCode());
        return new ResponseEntity<>(ErrorResponseDto.res(invalidTokenException), httpStatus);
    }

    // 원인을 알 수 없는 예외 처리
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception exception) {
        return new ResponseEntity<>(
                ErrorResponseDto.res(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), exception),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException methodArgumentNotValidException) {
        this.writeLog(methodArgumentNotValidException);
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

    private void writeLog(CustomException customException) {
        String exceptionName = customException.getClass().getSimpleName();
        ErrorCode errorCode = customException.getErrorCode();
        String detail = customException.getDetail();

        log.error("({}){}: {}", exceptionName, errorCode.getMessage(), detail);
    }

    private void writeLog(Exception exception) {
        log.error("({}){}", exception.getClass().getSimpleName(), exception.getMessage());
    }
}
