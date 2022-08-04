package GoGetters.GoGetter.service;

import GoGetters.GoGetter.domain.Receiver;
import GoGetters.GoGetter.domain.SecurityMember;
import GoGetters.GoGetter.domain.Sender;
import GoGetters.GoGetter.domain.Member;
import GoGetters.GoGetter.dto.RequestDto.MemberInfoRequest;
import GoGetters.GoGetter.repository.MemberRepository;
import GoGetters.GoGetter.repository.ReceiverRepository;
import GoGetters.GoGetter.repository.SenderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final SenderRepository senderRepository;
    private final ReceiverRepository receiverRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(Member member) throws InterruptedException {
        validateDuplicateUser(member);
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.encodePassword(encodedPassword);
        Long saveId = memberRepository.save(member);
        return saveId;
    }

    private void validateDuplicateUser(Member member) throws InterruptedException {
        List<Member> findMembers = memberRepository.findUserByEmail(member.getEmail());
        if(!findMembers.isEmpty()){
            throw new InterruptedException("이미 존재하는 회원입니다.");
        }
    }

    public User findUserByEmail(String email) {
        List<Member> members = memberRepository.findUserByEmail(email);
        if(members.isEmpty())
            throw new UsernameNotFoundException(email + " : 사용자 존재하지 않음");
        return new SecurityMember(members.get(0));
    }

    public User loadUserbyUsername(String username) {
        log.debug("load username :{}",username);
        List<Member> members= memberRepository.loadUserbyUsername(username);
        log.debug("load user by username :{}",members.size());
        if(members.isEmpty())
            throw new UsernameNotFoundException(username + " : 사용자 존재하지 않음");
        return new SecurityMember(members.get(0));
    }
    public Member loginUser(String email, String password) throws Exception {
        List<Member> members = memberRepository.findUserByEmail(email);
        if(members.isEmpty())
            throw new Exception("멤버가 조회되지 않음");
        Member member = members.get(0);
        //입력받은 패스워드를 통해 member 비밀번호와 일치하는지 확인
        if(!isPasswordSame(member,password))
            throw new Exception("아이디 또는 비밀번호가 틀립니다.");
        //

        return member;
    }

    private Boolean isPasswordSame(Member member, String password) {
        if (!passwordEncoder.matches(password, member.getPassword())) {
            System.out.println("비밀번호가 일치하지 않습니다");
            return false;
        }
        return true;
    }
    public Member findMemberByMemberId(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public Sender sender(Long memberId) {
        List<Sender> senders = memberRepository.findSender(memberId);
        log.info("Log message service | sender id :{}",senders.size());
        Sender sender;
        if (senders.isEmpty()) {
            Member findMember = memberRepository.findOne(memberId);
            sender=new Sender(findMember);
            senderRepository.save(sender);
        }
        else {
            sender=senders.get(0);
        }
        return sender;
    }
    @Transactional
    public Receiver receiver(Long memberId){

        List<Receiver> receivers= memberRepository.findReceiver(memberId);
        log.info("Log message service | sender id :{}",receivers.size());
        Receiver receiver;
        if (receivers.isEmpty()) {
            Member findMember = memberRepository.findOne(memberId);
            receiver=new Receiver(findMember);
            receiverRepository.save(receiver);
        }
        else {
            receiver=receivers.get(0);
        }
        return receiver;
    }

    public List<Member> findMemberByUsername(String username) {
        return memberRepository.findMembersByUsername(username);
    }

    public List<Member> findMemberByEmail(String email) {
        return memberRepository.findUserByEmail(email);
    }

    @Transactional
    public Long updateMyInfo(MemberInfoRequest memberInfoDto) {
        return memberRepository.updateMemberInfo(memberInfoDto);
    }
}
