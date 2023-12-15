package ra.academy.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ra.academy.exception.LoginFailException;

@RestControllerAdvice
public class AuthControllerAdvice {
    @ExceptionHandler(LoginFailException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String loginFail(LoginFailException e){
        return e.getMessage();
    }
}
