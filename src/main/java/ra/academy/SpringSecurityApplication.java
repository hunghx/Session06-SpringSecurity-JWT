package ra.academy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ra.academy.entity.Role;
import ra.academy.entity.RoleName;
import ra.academy.entity.User;
import ra.academy.service.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new  BCryptPasswordEncoder();
    }

//    @Bean
//    CommandLineRunner runner(UserService userService) throws ParseException {
//
//       return arg->{
//           SimpleDateFormat sff = new SimpleDateFormat("yyyy/MM/dd");
//
//           Role admin = new Role(null, RoleName.ROLE_ADMIN);
//           Role pm = new Role(null, RoleName.ROLE_PM);
//           Role user = new Role(null, RoleName.ROLE_USER);
//
//
//           User user1 =new User(null,"hunghx",
//                   passwordEncoder().encode("hung1234")
//                   ,"Hồ Xuân Hùng","hunghx@gmail.com",true,
//                   sff.parse("1999/10/10"),new HashSet<>()
//           );
//
//           user1.getRoles().add(admin);
//           user1.getRoles().add(pm);
//
//
//           User user2 =new User(null,"sonth",
//                   passwordEncoder().encode("sonsoi123")
//                   ,"Trần Hồng Sơn","sontx@gmail.com",true,
//                   sff.parse("1998/1/1"),new HashSet<>()
//           );
//
//           user2.getRoles().add(user);
//
//           userService.save(user1);
//           userService.save(user2);
//       };
//    }

}
