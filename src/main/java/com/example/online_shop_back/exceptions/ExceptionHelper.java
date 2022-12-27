package com.example.online_shop_back.exceptions;

import com.example.online_shop_back.security.component.MessageService;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.ErrorData;
import com.example.online_shop_back.utils.AppConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionHelper {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ApiResult<ErrorData>> handleException(MethodArgumentNotValidException ex) {
        List<ErrorData> errors = new ArrayList<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        fieldErrors.forEach(fieldError -> errors.add(
                new ErrorData(
                        getMessage(fieldError.getDefaultMessage()),
                        fieldError.getField(),
                        getErrorCode(fieldError.getDefaultMessage())
                )));

        return new ResponseEntity<>(ApiResult.errorResponse(errors), HttpStatus.BAD_REQUEST);
    }

    private String getMessage(String defaultMessage) {
        if (Objects.nonNull(defaultMessage) && defaultMessage.contains("/")) {
            Function<String, String> messageFunc = s -> s.split("/")[0];
            return messageFunc.apply(defaultMessage);
        } else {
            return defaultMessage;
        }
    }

    private int getErrorCode(String defaultMessage) {
        if (Objects.nonNull(defaultMessage) && defaultMessage.contains("/")) {
            Function<String, String> messageFunc = s -> s.split("/")[1];
            return Integer.parseInt(messageFunc.apply(defaultMessage));
        } else {
            return 0;
        }
    }

    @ExceptionHandler(value = {TypeMismatchException.class})
    public ResponseEntity<ApiResult<ErrorData>> handleException(TypeMismatchException ex) {
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<ApiResult<ErrorData>> handleException(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), AppConstant.CONFLICT),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<ApiResult<ErrorData>> handleException(MissingServletRequestParameterException ex) {
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ServletRequestBindingException.class})
    public ResponseEntity<ApiResult<ErrorData>> handleException(ServletRequestBindingException ex) {
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {MissingServletRequestPartException.class})
    public ResponseEntity<ApiResult<ErrorData>> handleException(MissingServletRequestPartException ex) {
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BindException.class})
    public ResponseEntity<ApiResult<ErrorData>> handleException(BindException ex) {
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<ApiResult<ErrorData>> handleExceptionAccessDenied(AccessDeniedException ex) {
        return new ResponseEntity<>(
                ApiResult.errorResponse(MessageService.getMessage("FORBIDDEN_EXCEPTION"), AppConstant.ACCESS_DENIED),
                HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(value = {MissingPathVariableException.class})
    public ResponseEntity<ApiResult<ErrorData>> handleExceptionNotFound(MissingPathVariableException ex) {
        return new ResponseEntity<>(
                ApiResult.errorResponse(MessageService.getMessage("PATH_NOTFOUND_EXCEPTION"), AppConstant.NOT_FOUND),
                HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public ResponseEntity<ApiResult<ErrorData>> handleException(NoHandlerFoundException ex) {
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 404),
                HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ApiResult<ErrorData>> handleException(HttpRequestMethodNotSupportedException ex) {
        return new ResponseEntity<>(
                ApiResult.errorResponse("Method error", 405),
                HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = {HttpMediaTypeNotAcceptableException.class})
    public ResponseEntity<ApiResult<ErrorData>> handleExceptionHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex) {
        return new ResponseEntity<>(
                ApiResult.errorResponse("No acceptable", 406),
                HttpStatus.NOT_ACCEPTABLE);
    }


    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<ApiResult<ErrorData>> handleExceptionHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        return new ResponseEntity<>(
                ApiResult.errorResponse(MessageService.getMessage("UNSUPPORTED_MEDIA_TYPE"), 415),
                HttpStatus.METHOD_NOT_ALLOWED);
    }


    @ExceptionHandler(value = {ConversionNotSupportedException.class})
    public ResponseEntity<ApiResult<ErrorData>> handleException(ConversionNotSupportedException ex) {
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), AppConstant.SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = {HttpMessageNotWritableException.class})
    public ResponseEntity<ApiResult<ErrorData>> handleException(HttpMessageNotWritableException ex) {
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), AppConstant.SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ApiResult<ErrorData>> handleException(Exception ex) {
        log.error("EXCEPTION_HELPER:", ex);
        ex.printStackTrace();
        return new ResponseEntity<>(
                ApiResult.errorResponse(
                        MessageService.getMessage("ERROR_IN_SERVER"),
                        AppConstant.SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {AsyncRequestTimeoutException.class})
    public ResponseEntity<ApiResult<ErrorData>> handleException(AsyncRequestTimeoutException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 503),
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(value = {RestException.class})
    public ResponseEntity<ApiResult<ErrorData>> handleException(RestException ex) {
        ex.printStackTrace();

        //AGAR RESOURSE TOPILMAGANLIGI XATOSI BO'LSA CLIENTGA QAYSI TABLEDA NIMA TOPILMAGANLIGI HAQIDA XABAR QAYTADI
        if (ex.getFieldName() != null)
            return new ResponseEntity<>(ApiResult.errorResponse(ex.getUserMsg(), ex.getErrorCode()), ex.getStatus());
        //AKS HOLDA DOIMIY EXCEPTIONLAR ISHLAYVERADI
        if (ex.getErrors() != null)
            return new ResponseEntity<>(ApiResult.errorResponse(ex.getErrors()), ex.getStatus());
        return new ResponseEntity<>(ApiResult.errorResponse(ex.getUserMsg(), ex.getErrorCode() != null ? ex.getErrorCode() : ex.getStatus().value()), ex.getStatus());
    }


}
