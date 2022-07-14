package GoGetters.GoGetter.repository;

import GoGetters.GoGetter.domain.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
class MessageRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    SenderRepository senderRepository;

    @Autowired
    ReceiverRepository receiverRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public void 쪽지작성 () throws Exception{
        //given
        Member member1 = new Member("rlagudtn4510@naver.com", "호로로","1234", 20, Gender.MALE);
        memberRepository.save(member1);
        Member member2 = new Member("rlagudtn@naver.com", "호로로","1234", 21, Gender.MALE);
        memberRepository.save(member2);

        Sender sender = new Sender(member1);
        Receiver receiver=new Receiver(member2);

        senderRepository.save(sender);
        receiverRepository.save(receiver);
        //when
        Message message=new Message(sender,receiver,"메시지 작성");
        Long save = messageRepository.save(message);
        Message foundMessage = messageRepository.findMessage(save);

        //then
        Assertions.assertThat(foundMessage.getContent()).isEqualTo("메시지 작성");
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void 쪽지목록조회 () throws Exception{
        //given
        Member member1 = new Member("rlagudtn4510@naver.com", "호로로","1234", 20, Gender.MALE);
        memberRepository.save(member1);
        Member member2 = new Member("rlagudtn@naver.com", "호로로","1234", 21, Gender.MALE);
        memberRepository.save(member2);

        Sender sender = new Sender(member1);
        Receiver receiver=new Receiver(member2);

        senderRepository.save(sender);
        receiverRepository.save(receiver);

        for(int i=0;i<10;i++){
            Message message=new Message(sender,receiver,"메시지 작성"+i);
            messageRepository.save(message);
        }

        Sender sender1=new Sender(member2);
        Receiver receiver1=new Receiver(member1);
        senderRepository.save(sender1);
        receiverRepository.save(receiver1);
        for(int i=0;i<10;i++){
            Message message=new Message(sender1,receiver1,"메시지 작성"+i);
            messageRepository.save(message);
        }
        //when
        List<Message> messages = messageRepository.findMessagesByReceiverId(receiver.getId());
        //then
        Assertions.assertThat(messages.size()).isEqualTo(10);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void 특정쪽지조회 () throws Exception{
        //given
        Member member1 = new Member("rlagudtn4510@naver.com", "호로로","1234", 20, Gender.MALE);
        memberRepository.save(member1);
        Member member2 = new Member("rlagudtn@naver.com", "호로로","1234", 21, Gender.MALE);
        memberRepository.save(member2);

        Sender sender = new Sender(member1);
        Receiver receiver=new Receiver(member2);

        senderRepository.save(sender);
        receiverRepository.save(receiver);

        for(int i=0;i<10;i++){
            Message message=new Message(sender,receiver,"메시지 작성"+i);
            messageRepository.save(message);
        }

        //when
        Message message = messageRepository.findMessage(3L);

        //then
        Assertions.assertThat(message.getId()).isEqualTo(3L);
    }
}