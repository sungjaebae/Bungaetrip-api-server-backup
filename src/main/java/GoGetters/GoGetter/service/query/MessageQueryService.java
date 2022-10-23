package GoGetters.GoGetter.service.query;

import GoGetters.GoGetter.domain.member.Member;
import GoGetters.GoGetter.domain.message.Message;
import GoGetters.GoGetter.domain.message.Receiver;
import GoGetters.GoGetter.domain.message.Sender;
import GoGetters.GoGetter.dto.message.MessageRequest;
import GoGetters.GoGetter.dto.message.ReceivedMessageResponse;
import GoGetters.GoGetter.dto.message.SentMessageResponse;
import GoGetters.GoGetter.repository.MemberRepository;
import GoGetters.GoGetter.repository.MessageRepository;
import GoGetters.GoGetter.repository.ReceiverRepository;
import GoGetters.GoGetter.repository.SenderRepository;
import GoGetters.GoGetter.util.FirebaseSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MessageQueryService {
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;
    private final SenderRepository senderRepository;
    private final ReceiverRepository receiverRepository;
    private final FirebaseSender firebaseSender;
    public List<ReceivedMessageResponse> listReceivedMessage(Long receiverId) {
        List<Message> messages=messageRepository.findMessagesByReceiverId(receiverId);
        return messages.stream().map(m ->
                new ReceivedMessageResponse(m)).collect(Collectors.toList());

    }

    public List<SentMessageResponse> listSentMessages(Long memberId) {
        List<Message> sentMessages= messageRepository.findSentMessagesByMemberId(memberId);
        return sentMessages.stream()
                .map(message -> new SentMessageResponse(message))
                .collect(Collectors.toList());
    }

    @Transactional
    public Long createMessage(MessageRequest messageRequest) throws IOException {
        //senderId, receiverId를 통해 sender,receiver 찾기
        Sender sender= this.sender(messageRequest.getSenderId());
        Receiver receiver= this.receiver(messageRequest.getReceiverId());

        //메세지 작성
        Message message=new Message(messageRequest.getContent());
        Long messageId = this.send(sender,receiver,message);
        this.sendMessageToFCM(receiver,messageId);

        return messageId;

    }
    public Long send(Sender sender, Receiver receiver, Message message) {
        senderRepository.save(sender);
        receiverRepository.save(receiver);
        message.setMembers(sender, receiver);
        return messageRepository.save(message);
    }
    private Sender sender(Long memberId) {
        Member findMember = memberRepository.findOne(memberId);

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

    private Receiver receiver(Long memberId){
        Member findMember = memberRepository.findOne(memberId);

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
    private void sendMessageToFCM(Receiver receiver,Long messageId) throws IOException {
        //1. 디바이스 정보 가져오기
        String fcmToken = receiver.getMember().getFcmToken();
        log.info("Fcm token {}",fcmToken);

        //2. fcm 에 전송하기
        String title = "알림";
        String body = "새 메세지가 도착했습니다";
        firebaseSender.pushBrowserSend(fcmToken,title,body,String.valueOf(messageId));
    }
}
