package ra.academy.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ra.academy.security.principle.UserDetailServiceImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthTokenFilter extends OncePerRequestFilter {
    private final UserDetailServiceImpl userDetailService;
    private final JwtProvider  jwtProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            // lấy token từ request
        try{
        String token = getTokenFromRequest(request);
        if (token!=null&&jwtProvider.validateToken(token)){
            String username = jwtProvider.getUserNameFromToken(token);
            UserDetails userDetails = userDetailService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new
                    UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }}catch (Exception e){
            log.error("Error->>>>>>",e.getMessage());
//            response.setHeader("error",e.getMessage());
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            Map<String ,String> map =new HashMap<>();
//            map.put("message_error",e.getMessage());
//            new ObjectMapper().writeValue(response.getOutputStream(),map);
        }
        filterChain.doFilter(request,response);
    }

    private String getTokenFromRequest(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if (header!=null&&header.startsWith("Bearer ")){
            return header.substring("Bearer ".length());
        }
        return null;
    }
}
