package com.example.clientes.exceptions;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    /*
     * Crear clase GlobalExceptionHandler
     * Para usar ErrorDetails para devolver la respuesta de error,
     * creemos una clase GlobalExceptionHandler anotada con la anotación @ControllerAdvice.
     * Esta clase maneja las excepciones globales y específicas de excepción en un solo lugar.
     */

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequestException(BadRequestException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * ******** Validar campos *************
     */
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
//
//        Map<String, String> errors = new HashMap<>();
//
//        ex.getBindingResult().getAllErrors().forEach(error -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//
//        String valor = errors.values().stream().findFirst().orElseGet(null);
//
//        ErrorDetails errorDetails = new ErrorDetails(new Date(), valor, request.getDescription(false));
//        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
//    }


}