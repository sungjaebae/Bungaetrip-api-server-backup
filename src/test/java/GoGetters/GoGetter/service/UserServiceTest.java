package GoGetters.GoGetter.service;

import GoGetters.GoGetter.domain.Gender;
import GoGetters.GoGetter.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.lang.reflect.Member;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    @Rollback(value = false)
    public void  일반회원가입() throws Exception{
        //given
        User user = new User("rlagudtn4510@naver.com","호로로", "1234", 20, Gender.MALE);

        //when
        Long saveId=userService.join(user);

        //then
        User findUser=userService.findUser(saveId);
        assertEquals(findUser.getEmail(),user.getEmail());
    }

    @Test
    @Rollback(value = false)
    public void 중복회원검사 () throws Exception{
        //given
        User user = new User("rlagudtn4510@naver.com","호로로", "1234", 20, Gender.MALE);
        userService.join(user);

        //when
        User user1 = new User("rlagudtn4510@naver.com","호로로", "1234", 20, Gender.MALE);
        //then
        IllegalStateException thrown=assertThrows(IllegalStateException.class,()->
            userService.join(user1));
        assertEquals("이미 존재하는 회원입니다.",thrown.getMessage());
    }



}