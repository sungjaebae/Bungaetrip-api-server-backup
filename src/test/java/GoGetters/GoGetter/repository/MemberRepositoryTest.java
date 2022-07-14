package GoGetters.GoGetter.repository;

import GoGetters.GoGetter.domain.Gender;
import GoGetters.GoGetter.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public void 회원생성() throws Exception {
        //given
        Member member = new Member("rlagudtn4510@naver.com","호로로", "1234", 20, Gender.MALE);
        //when
        Long savedId = memberRepository.save(member);
        Member find = memberRepository.findOne(savedId);

        //then
        Assertions.assertThat(find.getId()).isEqualTo(member.getId());
    }




}