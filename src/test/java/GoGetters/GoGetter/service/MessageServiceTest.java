package GoGetters.GoGetter.service;

import GoGetters.GoGetter.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MessageServiceTest {
    @Autowired
    MessageService messageService;
    @Autowired
    MemberService memberService;
    @Test
    public void  메시지전송() throws Exception{
        //given
        Member member = new Member("rlagudtn4510@naver.com","호로로", "1234", 20, Gender.MALE);
        memberService.join(member);
        Member member1 = new Member("rlagudtn45@naver.com","호로로", "1234", 20, Gender.MALE);
        memberService.join(member1);

        Member memberA = memberService.findUser(1L);
        Member memberB = memberService.findUser(2L);
        Sender sender=new Sender(memberA);
        Receiver receiver=new Receiver(memberB);
        Message message=new Message("메시지 내용");
        //when
        Long messageId=messageService.send(sender,receiver,message);
        Message findMessage = messageService.findMessage(messageId);

        //then
        assertEquals(findMessage.getReceiver().getMember().getId(),2L);
        assertEquals(findMessage.getSender().getMember().getId(),1L);
        assertEquals(findMessage.getId(),messageId);
    }
    
    @Test
    public void 받은메시지조회 () throws Exception{
        //given

        Member member = new Member("rlagudtn4510@naver.com","호로로", "1234", 20, Gender.MALE);
        memberService.join(member);
        Member member1 = new Member("rlagudtn45@naver.com","호로로", "1234", 20, Gender.MALE);
        memberService.join(member1);
        Member memberA = memberService.findUser(1L);
        Member memberB = memberService.findUser(2L);

        Sender sender=new Sender(memberA);
        Sender sender1=new Sender(memberB);
        List<Sender> senders = new ArrayList<>();
        senders.add(sender);
        senders.add(sender1);
        Receiver receiver=new Receiver(memberB);
        Receiver receiver1=new Receiver(memberA);
        List<Receiver> receivers = new ArrayList<>();
        receivers.add(receiver);
        receivers.add(receiver1);
        for(int i=0;i<10;i++){
            Message message=new Message("메시지 내용"+i);
            messageService.send(senders.get(i%2),receivers.get(i%2),message);
        }


        //when
        List<Message> findUserBReceived = messageService.findAllMessages(2L);

        //then
        assertEquals(findUserBReceived.size(),5);
        for(int i=0;i<findUserBReceived.size();i++){
            assertEquals(findUserBReceived.get(i).getReceiver().getMember().getId(),2L);
        }
    }
    
    @Test
    @Rollback(value = true)
    public void 메시지내용보기 () throws Exception{
        //given

        Member member = new Member("rlagudtn4510@naver.com","호로로", "1234", 20, Gender.MALE);
        memberService.join(member);
        Member member1 = new Member("rlagudtn40@naver.com","호로로", "1234", 20, Gender.MALE);
        memberService.join(member1);
        Member memberA = memberService.findUser(1L);
        Member memberB = memberService.findUser(2L);

        Sender sender=new Sender(memberA);
        Sender sender1=new Sender(memberB);
        List<Sender> senders = new ArrayList<>();
        senders.add(sender);
        senders.add(sender1);

        Receiver receiver=new Receiver(memberB);
        Receiver receiver1=new Receiver(memberA);
        List<Receiver> receivers = new ArrayList<>();
        receivers.add(receiver);
        receivers.add(receiver1);

        for(int i=0;i<10;i++){
            Message message=new Message("메시지 내용"+i);
            messageService.send(senders.get(i%2),receivers.get(i%2),message);
            System.out.println(senders.get(i%2).getId());
            System.out.println(receivers.get(i%2).getId());

        }

        //when
        Message findMessage=messageService.findMessage(2L);

        //then
        assertEquals(findMessage.getId(),2L);
        assertEquals(findMessage.getSender().getMember().getId(), memberB.getId());
        assertEquals(findMessage.getReceiver().getMember().getId(), memberA.getId());
    }
}