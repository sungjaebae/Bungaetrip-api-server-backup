package GoGetters.GoGetter.api;

import GoGetters.GoGetter.domain.Message;
import GoGetters.GoGetter.domain.Receiver;
import GoGetters.GoGetter.domain.Sender;
import GoGetters.GoGetter.dto.MessageDto;
import GoGetters.GoGetter.dto.MessageRequest;
import GoGetters.GoGetter.dto.ResponseDto.Success;
import GoGetters.GoGetter.dto.returnDto.MemberReturnDto;
import GoGetters.GoGetter.service.MessageService;
import GoGetters.GoGetter.service.MemberService;
import GoGetters.GoGetter.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MessageApiController {
    private final MessageService messageService;
    private final MemberService memberService;

//    @GetMapping(value = "/messages")
//    public ResponseEntity listAllMessages(){
//        List<Message> messages= messageService.findAllMessages();
//        List<MessageDto> collect = messages.stream().map(m ->
//                new MessageDto(m.getId(), m.getSender().getMember().getNickname()
//                        , m.getContent(), m.getCreated())).collect(Collectors.toList());
//        return ResponseUtil.successResponse(HttpStatus.OK, collect);
//    }
    //receiver_id 를 통해 message 목록 조회 api
    @GetMapping(value="/messages",params = "receiverId")
    public ResponseEntity listMessage(
                                      @RequestParam("receiverId") Long receiverId) {
        System.out.println("sender&receiver");
        List<Message> messages=messageService.findAllMessages(receiverId);
        List<MessageDto> collect = messages.stream().map(m ->
                new MessageDto(m.getId(),new MemberReturnDto(m.getSender().getMember()), m.getContent(),
                m.getCreated())).collect(Collectors.toList());

        return ResponseUtil.successResponse(HttpStatus.OK, collect);

    }

    //메시지 작성 api
    @PostMapping("/messages")
    public ResponseEntity createMessage(@RequestBody MessageRequest messageRequest){
        //senderId, receiverId를 통해 sender,receiver 찾기
        System.out.println(messageRequest.getReceiverId());
        Sender sender= memberService.findSender(messageRequest.getSenderId());
        Receiver receiver= memberService.findReceiver(messageRequest.getReceiverId());

        //메시지 작성
        Message message=new Message(messageRequest.getContent());
        Long messageId= messageService.send(sender,receiver,message);

        Map<String,Long> ret=new HashMap<>();
        ret.put("messageId",messageId);
        return ResponseUtil.successResponse(HttpStatus.OK, ret);

    }

    //message 내용 조회 api
    @GetMapping(value = "/messages",params = "messageId")
    public ResponseEntity readMessage(@RequestParam("messageId")Long messageId){
        Message message = messageService.findMessage(messageId);
        Sender sender=message.getSender();
        MessageDto messageDto=new MessageDto(message.getId(),new MemberReturnDto(sender.getMember()),
                message.getContent(),message.getCreated());

        return ResponseUtil.successResponse(HttpStatus.OK, messageDto);
    }

}
