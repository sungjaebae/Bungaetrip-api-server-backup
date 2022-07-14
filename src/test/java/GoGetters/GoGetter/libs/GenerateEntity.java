package GoGetters.GoGetter.libs;

import GoGetters.GoGetter.domain.Gender;
import GoGetters.GoGetter.domain.Member;
import GoGetters.GoGetter.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class GenerateEntity {
    @Autowired
    MemberRepository memberRepository;
    public List<Member> generateUsers(){
        for(int i=0;i<10;i++){
            Member member =new Member("rlagudtn"+i,"호로로","1234",20+i, Gender.MALE);
            memberRepository.save(member);

        }
        return memberRepository.findAllUsers();
    }
}
