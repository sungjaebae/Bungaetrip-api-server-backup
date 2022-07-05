package GoGetters.GoGetter.repository;

import GoGetters.GoGetter.domain.Gender;
import GoGetters.GoGetter.domain.User;
import GoGetters.GoGetter.libs.GenerateEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.lang.reflect.Member;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;


    @Test
    @Transactional
    @Rollback(value = false)
    public void 회원생성() throws Exception {
        //given
        User user = new User("rlagudtn4510@naver.com", "1234", 20, Gender.MALE);
        //when
        Long savedId = userRepository.save(user);
        User find = userRepository.findUser(savedId);

        //then
        Assertions.assertThat(find.getId()).isEqualTo(user.getId());
    }




}