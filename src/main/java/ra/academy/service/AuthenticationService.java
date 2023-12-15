package ra.academy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import ra.academy.dto.request.SignInDto;
import ra.academy.dto.response.AuthenticationResponse;
import ra.academy.entity.User;
import ra.academy.exception.LoginFailException;
import ra.academy.repository.IUserRepository;
import ra.academy.security.jwt.JwtProvider;
import ra.academy.security.principle.UserDetailImpl;
import ra.academy.security.principle.UserDetailServiceImpl;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final IUserRepository userRepository;
    private final JwtProvider jwtProvider;

    public AuthenticationResponse signIn(SignInDto signInDto) throws LoginFailException {
       try {
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInDto.getUsername(), signInDto.getPassword()));
       }catch (AuthenticationException  e){
           throw new LoginFailException("Username or password incorrect");
       }
        // nếu xác thực thành công
        User user = userRepository.findByUsername(signInDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Username notfound"));
        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken = jwtProvider.generateAccessToken(user);
        return AuthenticationResponse.builder()
                .username(user.getUsername())
                .fullName(user.getFullName())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(user.getEmail())
                .build();
    };
}
