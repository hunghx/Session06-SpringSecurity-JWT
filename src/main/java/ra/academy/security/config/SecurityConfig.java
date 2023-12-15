package ra.academy.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ra.academy.security.jwt.JwtAuthTokenFilter;
import ra.academy.security.jwt.JwtEntryPoint;
import ra.academy.security.jwt.JwtProvider;
import ra.academy.security.principle.UserDetailServiceImpl;

@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {
    @Autowired
    private JwtProvider jwtProvider;
    @Bean
    public JwtEntryPoint jwtEntryPoint() {
        return new JwtEntryPoint();
    }

    @Autowired
    public JwtAuthTokenFilter jwtAuthTokenFilter;

    ;

    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setPasswordEncoder(passwordEncoder);
        dao.setUserDetailsService(userDetailService);
        return dao;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }

    // cấu hình phân quyền
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtEntryPoint())) // xử lí lỗi liên quan đến xác thực
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // ko lưu trạng thái
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/v4/auth/**").permitAll() // công khai với đường dẫn này
                                .requestMatchers("/api/v4/test/**").permitAll()
                                .requestMatchers("/api/v4/admin/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers("/api/v4/pm/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_PM")
                                .requestMatchers("/api/v4/user/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER", "ROLE_PM")
                                .anyRequest().authenticated() // các đường dẫn khác yêu cầu xác thực
                );
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
}
