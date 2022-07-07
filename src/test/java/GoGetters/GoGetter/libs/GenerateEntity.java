package GoGetters.GoGetter.libs;

import GoGetters.GoGetter.domain.Gender;
import GoGetters.GoGetter.domain.User;
import GoGetters.GoGetter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class GenerateEntity {
    @Autowired
    UserRepository userRepository;
    public List<User> generateUsers(){
        for(int i=0;i<10;i++){
            User user=new User("rlagudtn"+i,"호로로","1234",20+i, Gender.MALE);
            userRepository.save(user);

        }
        return userRepository.findAllUsers();
    }
}
