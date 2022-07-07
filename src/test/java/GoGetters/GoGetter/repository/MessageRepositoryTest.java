package GoGetters.GoGetter.repository;

import GoGetters.GoGetter.domain.*;
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
class MessageRepositoryTest {
    @Autowired
    UserRepository userRepository;

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
        User user1 = new User("rlagudtn4510@naver.com", "호로로","1234", 20, Gender.MALE);
        userRepository.save(user1);
        User user2 = new User("rlagudtn@naver.com", "호로로","1234", 21, Gender.MALE);
        userRepository.save(user2);

        Sender sender = new Sender(user1);
        Receiver receiver=new Receiver(user2);

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
        User user1 = new User("rlagudtn4510@naver.com", "호로로","1234", 20, Gender.MALE);
        userRepository.save(user1);
        User user2 = new User("rlagudtn@naver.com", "호로로","1234", 21, Gender.MALE);
        userRepository.save(user2);

        Sender sender = new Sender(user1);
        Receiver receiver=new Receiver(user2);

        senderRepository.save(sender);
        receiverRepository.save(receiver);

        for(int i=0;i<10;i++){
            Message message=new Message(sender,receiver,"메시지 작성"+i);
            messageRepository.save(message);
        }

        Sender sender1=new Sender(user2);
        Receiver receiver1=new Receiver(user1);
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
        User user1 = new User("rlagudtn4510@naver.com", "호로로","1234", 20, Gender.MALE);
        userRepository.save(user1);
        User user2 = new User("rlagudtn@naver.com", "호로로","1234", 21, Gender.MALE);
        userRepository.save(user2);

        Sender sender = new Sender(user1);
        Receiver receiver=new Receiver(user2);

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