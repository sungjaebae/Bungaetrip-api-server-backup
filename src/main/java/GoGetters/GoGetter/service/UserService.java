package GoGetters.GoGetter.service;

import GoGetters.GoGetter.domain.Receiver;
import GoGetters.GoGetter.domain.Sender;
import GoGetters.GoGetter.domain.User;
import GoGetters.GoGetter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;





    public Long join(User user) {
        validateDuplicateUser(user);
        Long saveId = userRepository.save(user);
        return saveId;
    }

    private void validateDuplicateUser(User user) {
        List<User> findUsers = userRepository.findUserByEmail(user.getEmail());
        if(!findUsers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public User findUser(Long userId) {
        return userRepository.findUser(userId);
    }

    public Sender findSender(Long userId) {
        return userRepository.findSender(userId);
    }
    public Receiver findReceiver(Long userId){
        return userRepository.findReceiver(userId);
    }

}
