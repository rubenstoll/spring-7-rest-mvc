package guru.springframework.spring7restmvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by ruben
 **/
@Slf4j
//@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException() {
        log.error(" NOT FOUND EXCEPTION HANDLED with status {}", HttpStatus.NOT_FOUND.getReasonPhrase());
        return ResponseEntity.notFound().build();

    }
}
