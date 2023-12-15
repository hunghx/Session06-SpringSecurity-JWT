package ra.academy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.academy.dto.request.SignInDto;
import ra.academy.dto.response.AuthenticationResponse;
import ra.academy.exception.LoginFailException;
import ra.academy.service.AuthenticationService;

@RestController
@RequestMapping("/api/v4/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody SignInDto signInDto) throws LoginFailException {
        return new ResponseEntity<>(authenticationService.signIn(signInDto), HttpStatus.OK);
    }
}
