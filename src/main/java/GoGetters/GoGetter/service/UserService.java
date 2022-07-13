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


    public User loginUser(String email, String password) throws Exception {
        List<User> users = userRepository.findUserByEmail(email);
        if(users.isEmpty()) throw new Exception("멤버가 조회되지 않음");

        User user = users.get(0);

        //인코딩 해서 동일한지 확인하는 로직
        if(!isPasswordEqual(user,password))
            throw new Exception("아이디 또는 비밀번호가 일치하지 않습니다");
        return user;
    }
    private Boolean isPasswordEqual(User user,String password){
        String salt="string";
        password=  "salt 와 password 를 통해 인코딩";
        if(!user.getPassword().equals(password))
            return false;
        return true;
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
