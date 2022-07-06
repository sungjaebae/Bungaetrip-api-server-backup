package GoGetters.GoGetter.service;

import GoGetters.GoGetter.domain.Receiver;
import GoGetters.GoGetter.domain.Sender;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public Sender findSender(Long sender_id) {
        return  new Sender();
    }

    public Receiver findReceiver(Long receiver_id) {
        return new Receiver();
    }
}
