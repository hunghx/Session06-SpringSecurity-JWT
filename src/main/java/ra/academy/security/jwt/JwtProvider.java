package ra.academy.security.jwt;


import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ra.academy.entity.User;

import java.util.Date;

@Component
@Slf4j
public class JwtProvider {
    @Value("${jwt.sercret-key}")
    private String SECRET_KEY;
    @Value("${jwt.access-token.expired}")
    private long access_expired;
    @Value("${jwt.refresh-token.expired}")
    private long refresh_expired;

    public  String generateAccessToken(User user){
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+access_expired))
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY).compact();
    }
    public  String generateRefreshToken(User user){
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+refresh_expired))
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY).compact();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token); //Lưu ý chính tả
            return true;

        } catch (ExpiredJwtException e) {
            log.error("Failed -> Expired Token Message {}", e.getMessage());
        }catch (UnsupportedJwtException e) {
            log.error("Failed -> Unsupported Token Message {}", e.getMessage());
        }catch (MalformedJwtException e) {
            log.error("Failed -> Invalid Format Token Message {}", e.getMessage());
        }catch (SignatureException e) {
            log.error("Failed -> Invalid Signature Token Message {}", e.getMessage());
        }catch (IllegalArgumentException e) {
            log.error("Failed -> Claims Empty Token Message {}", e.getMessage());
        }
        return false;
    }

    public  String getUserNameFromToken(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token).getBody().getSubject(); // lưu ý chính tả
    }


}
