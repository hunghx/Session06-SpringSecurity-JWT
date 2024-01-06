package ra.academy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.academy.dto.request.SignInDto;
import ra.academy.dto.request.SignUpDto;
import ra.academy.dto.response.AuthenticationResponse;
import ra.academy.exception.LoginFailException;
import ra.academy.service.AuthenticationService;

@RestController
@RequestMapping("/api/v4/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody SignInDto signInDto) throws LoginFailException {
        return new ResponseEntity<>(authenticationService.signIn(signInDto), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@ModelAttribute SignUpDto signUpDto) {
        authenticationService.signUp(signUpDto);
        return new ResponseEntity<>("Register Success", HttpStatus.CREATED);
    }

    @PutMapping("/profile")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> editProfile(@ModelAttribute SignUpDto signUpDto){
        authenticationService.updateProfile(signUpDto);
        return new ResponseEntity<>("Update Success", HttpStatus.CREATED);

    }
    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> myProfile(){

        return new ResponseEntity<>("UserInfo", HttpStatus.CREATED);

    }
}
