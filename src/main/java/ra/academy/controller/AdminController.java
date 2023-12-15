package ra.academy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v4")
public class AdminController {
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/admin/home")
    public String home(){
        return "Successs";
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/pm/home")
    public String pm(){
        return "Successs";
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user/home")
    public String user(){
        return "Successs";
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/no-role/home")
    @PreAuthorize("hasRole('PM')")
    public String noRole(){
        return "Successs";
    }



}
