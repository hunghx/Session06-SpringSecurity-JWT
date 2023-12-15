package ra.academy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ra.academy.entity.User;
import ra.academy.repository.IUserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository userRepository;
    public void save (User user){
        userRepository.save(user);
    }
}
