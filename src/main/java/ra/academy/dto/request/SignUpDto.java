package ra.academy.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SignUpDto {
    private String username;
    private String password;
    private String fullName;
    private MultipartFile avatarUrl;
    private String email;
    private Boolean sex;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
}
