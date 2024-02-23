package dev.codeunlu.currencyapi.dto.common;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class BaseResponse <T>{
    private T data;
    private boolean success = true;
    private ExceptionInfo error;

    public BaseResponse(){

    }
    public BaseResponse(T data) {
        this.data = data;
    }

    public BaseResponse(T data, boolean success) {
        this.data = data;
        this.success = success;
    }

    public BaseResponse(T data, boolean success, ExceptionInfo error) {
        this.data = data;
        this.success = success;
        this.error = error;
    }
}
