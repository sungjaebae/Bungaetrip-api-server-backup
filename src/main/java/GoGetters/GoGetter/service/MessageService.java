package GoGetters.GoGetter.service;

import GoGetters.GoGetter.domain.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    public List<Message> findAllMessages(Long receiverId) {
        return new ArrayList<>();
    }

    public Long writeMessage(Message message) {
        return null;
    }

    public Message findMessage(Long messageId) {
        return new Message();
    }
}
