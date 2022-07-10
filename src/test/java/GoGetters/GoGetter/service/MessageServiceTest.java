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
    UserService userService;
    @Test
    @Rollback(value = false)
    public void  메시지전송() throws Exception{
        //given
        User user = new User("rlagudtn4510@naver.com","호로로", "1234", 20, Gender.MALE);
        userService.join(user);
        User user1 = new User("rlagudtn45@naver.com","호로로", "1234", 20, Gender.MALE);
        userService.join(user1);

        User userA = userService.findUser(1L);
        User userB = userService.findUser(2L);
        Sender sender=new Sender(userA);
        Receiver receiver=new Receiver(userB);
        Message message=new Message("메시지 내용");
        //when
        Long messageId=messageService.send(sender,receiver,message);
        Message findMessage = messageService.findMessage(messageId);

        //then
        assertEquals(findMessage.getReceiver().getUser().getId(),2L);
        assertEquals(findMessage.getSender().getUser().getId(),1L);
        assertEquals(findMessage.getId(),messageId);
    }
    
    @Test
    @Rollback(value = false)
    public void 받은메시지조회 () throws Exception{
        //given

        User user = new User("rlagudtn4510@naver.com","호로로", "1234", 20, Gender.MALE);
        userService.join(user);
        User user1 = new User("rlagudtn45@naver.com","호로로", "1234", 20, Gender.MALE);
        userService.join(user1);
        User userA = userService.findUser(1L);
        User userB = userService.findUser(2L);
        User userC=userService.findUser(8L);

        Sender sender=new Sender(userA);
        Sender sender1=new Sender(userB);
        List<Sender> senders = new ArrayList<>();
        senders.add(sender);
        senders.add(sender1);
        Receiver receiver=new Receiver(userB);
        Receiver receiver1=new Receiver(userA);
        List<Receiver> receivers = new ArrayList<>();
        receivers.add(receiver);
        receivers.add(receiver1);
        for(int i=0;i<10;i++){
            Message message=new Message("메시지 내용"+i);
            messageService.send(senders.get(i%2),receivers.get(i%2),message);
        }


        //when
        List<Message> findUserBReceived = messageService.findAllMessages(1L,2L);

        //then
        assertEquals(findUserBReceived.size(),5);
        for(int i=0;i<findUserBReceived.size();i++){
            assertEquals(findUserBReceived.get(i).getReceiver().getUser().getId(),2L);
        }
    }
    
    @Test
    public void 메시지내용보기 () throws Exception{
        //given

        User user = new User("rlagudtn4510@naver.com","호로로", "1234", 20, Gender.MALE);
        userService.join(user);
        User user1 = new User("rlagudtn40@naver.com","호로로", "1234", 20, Gender.MALE);
        userService.join(user1);
        User userA = userService.findUser(1L);
        User userB = userService.findUser(2L);
        User userC=userService.findUser(8L);

        Sender sender=new Sender(userA);
        Sender sender1=new Sender(userB);
        List<Sender> senders = new ArrayList<>();
        senders.add(sender);
        senders.add(sender1);
        Receiver receiver=new Receiver(userB);
        Receiver receiver1=new Receiver(userA);
        List<Receiver> receivers = new ArrayList<>();
        receivers.add(receiver);
        receivers.add(receiver1);
        for(int i=0;i<10;i++){
            Message message=new Message("메시지 내용"+i);
            messageService.send(senders.get(i%2),receivers.get(i%2),message);
        }

        //when
        Message findMessage=messageService.findMessage(2L);

        //then
        assertEquals(findMessage.getId(),2L);
        assertEquals(findMessage.getSender().getUser().getId(),userB.getId());
        assertEquals(findMessage.getReceiver().getUser().getId(),userA.getId());
    }
}