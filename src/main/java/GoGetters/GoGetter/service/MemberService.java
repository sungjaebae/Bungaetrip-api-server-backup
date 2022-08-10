package GoGetters.GoGetter.service;

import GoGetters.GoGetter.MessageResource;
import GoGetters.GoGetter.domain.Receiver;
import GoGetters.GoGetter.domain.SecurityMember;
import GoGetters.GoGetter.domain.Sender;
import GoGetters.GoGetter.domain.Member;
import GoGetters.GoGetter.dto.RequestDto.MemberInfoRequest;
import GoGetters.GoGetter.exception.Member.MemberAlreadyExistException;
import GoGetters.GoGetter.exception.Member.NoSuchMemberException;
import GoGetters.GoGetter.repository.MemberRepository;
import GoGetters.GoGetter.repository.ReceiverRepository;
import GoGetters.GoGetter.repository.SenderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public Member findOne(Long memberId) {
        List<Member> member = memberRepository.findOne(memberId);
        if (member.isEmpty()) {
            throw new NoSuchMemberException(MessageResource.memberNotExist);
        }
        return member.get(0);
    }
    public User loadUserbyUsername(String username) {
        List<Member> members= memberRepository.loadUserbyUsername(username);
        if(members.isEmpty())
            throw new UsernameNotFoundException(username + " : 사용자 존재하지 않음");
        return new SecurityMember(members.get(0));
    }
    @Transactional
    public void enrollFcmToken(Member member,String fcmToken) {

        member.enrollFcmToken(fcmToken);
        log.debug("update fcm token {}");


    }
    @Transactional
    public void updateFcmToken(Member member, String fcmToken) {
        member.updateFcmToken(fcmToken);
    }
    public List<Member> findMemberByUsername(String username) {
        return memberRepository.findMembersByUsername(username);
    }

    public List<Member> findMemberByEmail(String email) {
        return memberRepository.findUserByEmail(email);
    }

    @Transactional
    public Long updateMyInfo(MemberInfoRequest memberInfoDto) {
        Member member = this.findOne(memberInfoDto.getMemberId());
        return memberRepository.updateMemberInfo(member,memberInfoDto);
    }
    @Transactional
    public Sender sender(Long memberId) {
        Member findMember = this.findOne(memberId);

        List<Sender> senders = memberRepository.findSender(memberId);
        Sender sender;
        if (senders.isEmpty()) {
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
        Member findMember = this.findOne(memberId);

        List<Receiver> receivers= memberRepository.findReceiver(memberId);
        Receiver receiver;
        if (receivers.isEmpty()) {
            receiver=new Receiver(findMember);
            receiverRepository.save(receiver);
        }
        else {
            receiver=receivers.get(0);
        }
        return receiver;
    }



//    @Transactional
//    public Long join(Member member) throws InterruptedException {
//        validateDuplicateUser(member);
//        String encodedPassword = passwordEncoder.encode(member.getPassword());
//        member.encodePassword(encodedPassword);
//        Long saveId = memberRepository.save(member);
//        return saveId;
//    }

//    private void validateDuplicateUser(Member member) throws InterruptedException {
//        List<Member> findMembers = memberRepository.findUserByEmail(member.getEmail());
//        if(!findMembers.isEmpty()){
//            throw new MemberAlreadyExistException("이미 존재하는 회원입니다.");
//        }
//    }




//    public Member loginUser(String email, String password) throws Exception {
//        List<Member> members = memberRepository.findUserByEmail(email);
//        if(members.isEmpty())
//            throw new NoSuchMemberException("멤버가 조회되지 않음");
//        Member member = members.get(0);
//        //입력받은 패스워드를 통해 member 비밀번호와 일치하는지 확인
//        if(!isPasswordSame(member,password))
//            throw new Exception("아이디 또는 비밀번호가 틀립니다.");
//        //
//
//        return member;
//    }

//    private Boolean isPasswordSame(Member member, String password) {
//        if (!passwordEncoder.matches(password, member.getPassword())) {
//            return false;
//        }
//        return true;
//    }






}
