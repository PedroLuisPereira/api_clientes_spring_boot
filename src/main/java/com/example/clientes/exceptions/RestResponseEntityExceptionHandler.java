package com.example.clientes.exceptions;

import com.example.clientes.exceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 *Spring propone la creación de una clase Java con el propósito exclusivo de manejar el
comportamiento de la aplicación para cada una de las excepciones que sean lanzadas durante el
consumo de los servicios.
Esta clase debe tener la anotación @ControllerAdvice. Aquí se muestra cómo
implementarla. La podremos crear dentro del paquete controllers.
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(BadRequestException.class)
//    protected ResponseEntity<Object> handleBadRequest(RuntimeException runtimeException, WebRequest webRequest) {
//
//        Map<String, Object> body = new LinkedHashMap<>();
//        body.put("timestamp", LocalDateTime.now());
//        body.put("message", runtimeException.getMessage());
//        body.put("Error", HttpStatus.BAD_REQUEST.toString());
//
//        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
//    }
}
