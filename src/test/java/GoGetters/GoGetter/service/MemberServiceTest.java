package GoGetters.GoGetter.service;

import GoGetters.GoGetter.domain.Gender;
import GoGetters.GoGetter.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Test
    @Rollback(value = false)
    public void  일반회원가입() throws Exception{
        //given
        Member member = new Member("rlagudtn4510@naver.com","호로로", "1234", 20, Gender.MALE);

        //when
        Long saveId= memberService.join(member);

        //then
        Member findMember = memberService.findUser(saveId);
        assertEquals(findMember.getEmail(), member.getEmail());
    }

    @Test
    @Rollback(value = false)
    public void 중복회원검사 () throws Exception{
        //given
        Member member = new Member("rlagudtn4510@naver.com","호로로", "1234", 20, Gender.MALE);
        memberService.join(member);

        //when
        Member member1 = new Member("rlagudtn4510@naver.com","호로로", "1234", 20, Gender.MALE);
        //then
        IllegalStateException thrown=assertThrows(IllegalStateException.class,()->
            memberService.join(member1));
        assertEquals("이미 존재하는 회원입니다.",thrown.getMessage());
    }



}