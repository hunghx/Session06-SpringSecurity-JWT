package ra.academy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthenticationResponse {
    private String username;
    private String fullName;
    private String email;
    private final String type = "Bearer";
    private String accessToken;
    private String refreshToken;
}
