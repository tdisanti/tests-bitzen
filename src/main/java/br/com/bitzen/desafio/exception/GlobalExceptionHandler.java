package br.com.bitzen.desafio.exception;

import java.util.Calendar;
import java.util.NoSuchElementException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.bitzen.desafio.domain.exception.RequestError;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handle(Exception e, WebRequest webRequest, HttpServletRequest request) {
        log.error("Exception Occured:: URL: " + request.getRequestURL());
        log.error("Exception Occured:: EXCEPTION: " + e);
        log.error("Exception Occured:: MESSAGE: " + e.getMessage());

        HttpStatus httpStatus = getHttpStatus(e);

        RequestError requestError = RequestError.builder()
                .httpStatus(httpStatus.value())
                .httpValue(httpStatus.getReasonPhrase())
                .exception(e + "")
                .message(e.getMessage())
                .urlPath(request.getRequestURL().toString())
                .timestamp(Calendar.getInstance().getTime())
                .build();

        return this.handleExceptionInternal(e, (Object) requestError, (HttpHeaders) null, httpStatus, webRequest);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        return super.handleExceptionInternal(ex, body, new HttpHeaders(), status, webRequest);
    }

    private HttpStatus getHttpStatus(Exception ex) {
        if (ex instanceof NoSuchElementException
                || ex instanceof NotFoundException
                || ex instanceof EmptyResultDataAccessException) {
            return HttpStatus.NOT_FOUND;
        }
        else {
            return HttpStatus.BAD_REQUEST;
        }

    }

}
