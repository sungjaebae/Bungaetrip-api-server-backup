package GoGetters.GoGetter.service;

import GoGetters.GoGetter.domain.Message;
import GoGetters.GoGetter.domain.Receiver;
import GoGetters.GoGetter.domain.Sender;
import GoGetters.GoGetter.repository.MessageRepository;
import GoGetters.GoGetter.repository.ReceiverRepository;
import GoGetters.GoGetter.repository.SenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final SenderRepository senderRepository;
    private final ReceiverRepository receiverRepository;



    @Transactional
    public Long send(Sender sender, Receiver receiver, Message message) {
        senderRepository.save(sender);
        receiverRepository.save(receiver);
        message.setMembers(sender, receiver);
        return messageRepository.save(message);
    }
    public Message findMessage(Long messageId) {
        return messageRepository.findMessage(messageId);
    }
    public List<Message> findAllMessages(){
        return messageRepository.findAllMessages();
    }

    public List<Message> findAllMessages(Long receiverId) {
        return messageRepository.findMessagesByReceiverId(receiverId);
    }



}
