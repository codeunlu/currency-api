package dev.codeunlu.currencyapi.exception;

import dev.codeunlu.currencyapi.dto.common.BaseResponse;
import dev.codeunlu.currencyapi.dto.common.ExceptionInfo;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    @ExceptionHandler(RequestNotPermitted.class)
    public Object handleRequestNotPermitted(RequestNotPermitted exception){
        final ExceptionInfo exceptionInfo = ExceptionInfo
                .builder()
                .message(exception.getMessage())
                .detailedMessage(prepareDetailsForExceptionMessage(exception))
                .build();
        return BaseResponse.builder()
                .success(false)
                .error(exceptionInfo)
                .build();
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public Object handleNotFoundException(NotFoundException exception){
        final ExceptionInfo exceptionInfo = ExceptionInfo
                .builder()
                .message(exception.getMessage())
                .detailedMessage(prepareDetailsForExceptionMessage(exception))
                .build();
        return BaseResponse.builder()
                .success(false)
                .error(exceptionInfo)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentException(MethodArgumentNotValidException exception){
        List<String> errors = exception.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();
        final ExceptionInfo exceptionInfo = ExceptionInfo
                .builder()
                .message(errors.toString())
                .detailedMessage(prepareDetailsForExceptionMessage(exception))
                .build();
        return BaseResponse.builder()
                .success(false)
                .error(exceptionInfo)
                .build();
    }


    private String prepareDetailsForExceptionMessage(Exception exception) {
        StringBuilder message = new StringBuilder();
        message.append("Exception Stack Trace: ").append(Arrays.toString(exception.getStackTrace()));
        Throwable cauese = exception.getCause();

        while (cauese != null){
            message.append(", Cause Message").append(cauese);
            message.append(", Cause Stack Trace").append(Arrays.toString(cauese.getStackTrace()));
            cauese = cauese.getCause();
        }

        return message.toString();
    }
}
