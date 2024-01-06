package ra.academy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import ra.academy.dto.request.SignInDto;
import ra.academy.dto.request.SignUpDto;
import ra.academy.dto.response.AuthenticationResponse;
import ra.academy.entity.User;
import ra.academy.exception.LoginFailException;
import ra.academy.repository.IUserRepository;
import ra.academy.security.jwt.JwtProvider;
import ra.academy.security.principle.UserDetailImpl;
import ra.academy.security.principle.UserDetailServiceImpl;

import javax.annotation.Signed;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final IUserRepository userRepository;
    private final JwtProvider jwtProvider;
    private  final UploadService uploadService;
    private  final PasswordEncoder passwordEncoder;


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

    public void signUp(SignUpDto signUpDto){
        String avatarUrl = null;
        if (signUpDto.getAvatarUrl().getSize()!=0) {
            avatarUrl = uploadService.uploadFileToServer(signUpDto.getAvatarUrl());
        }
        User u = User.builder()
                .username(signUpDto.getUsername())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .avatarUrl(avatarUrl)
                .sex(signUpDto.getSex())
                .birthday(signUpDto.getBirthday())
                .fullName(signUpDto.getFullName())
                .email(signUpDto.getEmail())
                .build();
        userRepository.save(u);
    }


    public void updateProfile(SignUpDto signUpDto){
        UserDetails  userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userInfo = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Userrname notfound"));
        String avatarUrl = userInfo.getAvatarUrl();
        if (signUpDto.getAvatarUrl().getSize()!=0) {
            avatarUrl = uploadService.uploadFileToServer(signUpDto.getAvatarUrl());
            boolean check = uploadService.deleteFile(userInfo.getAvatarUrl().substring("https://storage.googleapis.com/download/storage/v1/b/upload-firebase-in-java.appspot.com/o/".length()));
            log.info("check =>>>>>",check);
        }
        userInfo.setBirthday(signUpDto.getBirthday());
        userInfo.setAvatarUrl(avatarUrl);
        userInfo.setFullName(signUpDto.getFullName());
        userInfo.setEmail(signUpDto.getEmail());
        userInfo.setSex(signUpDto.getSex());
        userRepository.save(userInfo);
    }
}
