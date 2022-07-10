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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MessageApiController {
    private final MessageService messageService;
    private final UserService userService;

    @GetMapping(value = "/messages")
    public Result listAllMessages(){
        System.out.println("messages all");
        List<Message> messages= messageService.findAllMessages();
        List<MessageDto> collect = messages.stream().map(m ->
                new MessageDto(m.getId(), m.getSender().getUser().getNickName()
                        , m.getContent(), m.getCreated())).collect(Collectors.toList());
        return new Result(collect);
    }
    //receiver_id 를 통해 message 목록 조회 api
    @GetMapping(value="/messages",params = {"senderId","receiverId"})
    public Result listMessage(@RequestParam("senderId")Long senderId, @RequestParam("receiverId") Long receiverId) {
        System.out.println("sender&receiver");
        List<Message> messages=messageService.findAllMessages(senderId,receiverId);
        List<MessageDto> collect = messages.stream().map(m ->
                new MessageDto(m.getId(),m.getSender().getUser().getNickName(), m.getContent(),
                m.getCreated())).collect(Collectors.toList());

        return new Result(collect);
    }

    //메시지 작성 api
    @PostMapping("/messages")
    public Result createMessage(@RequestBody MessageRequest messageRequest){
        System.out.println(messageRequest.getContent());
        //senderId, receiverId를 통해 sender,receiver 찾기
        Sender sender=userService.findSender(messageRequest.getSenderId());
        Receiver receiver=userService.findReceiver(messageRequest.getReceiverId().get(0));

        //메시지 작성
        Message message=new Message(messageRequest.getContent());
        Long messageId= messageService.send(sender,receiver,message);

        Map<String,Long> ret=new HashMap<>();
        ret.put("messageId",messageId);
        return new Result(ret);
    }

    //message 내용 조회 api
    @GetMapping(value = "/messages",params = "messageId")
    public Result readMessage(@RequestParam("messageId")Long messageId){
        Message message = messageService.findMessage(messageId);
        MessageDto messageDto=new MessageDto(message.getId(),message.getSender().getUser().getNickName(),
                message.getContent(),message.getCreated());

        return new Result(messageDto);
    }

}
