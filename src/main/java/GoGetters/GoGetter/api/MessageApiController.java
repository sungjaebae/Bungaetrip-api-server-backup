package GoGetters.GoGetter.api;

import GoGetters.GoGetter.domain.Message;
import GoGetters.GoGetter.domain.Receiver;
import GoGetters.GoGetter.domain.Sender;
import GoGetters.GoGetter.domain.User;
import GoGetters.GoGetter.dto.MessageDto;
import GoGetters.GoGetter.dto.MessageRequest;
import GoGetters.GoGetter.dto.Result;
import GoGetters.GoGetter.service.MessageService;
import GoGetters.GoGetter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MessageApiController {
    private final MessageService messageService;
    private final UserService userService;
    //receiver_id 를 통해 message 목록 조회 api
    @GetMapping("/messages")
    public Result listMessage(@RequestParam("receiver_id") Long receiverId) {
        List<Message> messages=messageService.findAllMessages(receiverId);
        List<MessageDto> collect = messages.stream().map(m ->
                new MessageDto(m.getId(),m.getSender().getUser().getNickName(), m.getContent(),
                m.getCreated())).collect(Collectors.toList());

        return new Result(collect);
    }

    //메시지 작성 api
    @PostMapping("/messages")
    public Result createMessage(MessageRequest messageRequest){
        //sender_id, receiver_id를 통해 sender,receiver 찾기
        Sender sender=userService.findSender(messageRequest.getSender_id());
        Receiver receiver=userService.findReceiver(messageRequest.getReceiver_id());

        //메시지 작성
        Message message=new Message(sender,receiver,messageRequest.getContent() );
        Long messageId= messageService.writeMessage(message);

        return new Result(messageId);
    }

    //message 내용 조회 api
    @GetMapping("/message")
    public Result readMessage(@RequestParam("message_id")Long messageId){
        Message message = messageService.findMessage(messageId);
        MessageDto messageDto=new MessageDto(message.getId(),message.getSender().getUser().getNickName(),
                message.getReceiver().getUser().getNickName(),message.getCreated());

        return new Result(messageDto);
    }


}
