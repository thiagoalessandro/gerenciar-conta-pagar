package br.com.intelector.gerenciarcontapagar.controller.handler;

import br.com.intelector.gerenciarcontapagar.controller.dto.response.Response;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Response<List<String>> response = new Response<>();
        response.getErrors().add(ex.getCause().getMessage());
        return super.handleExceptionInternal(ex, response, headers, HttpStatus.OK, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        Response<List<String>> response = new Response<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            response.getErrors().add(fieldError.getDefaultMessage());
        }
        return super.handleExceptionInternal(ex, response, headers, HttpStatus.OK, request);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> constraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        Response<List<String>> response = new Response<>();
        for (ConstraintViolation<?> violation : violations) {
            response.getErrors().add(violation.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity illegalArgumentException(IllegalArgumentException exception) {
        Response<List<String>> response = new Response<>();
        response.getErrors().add(exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

}
