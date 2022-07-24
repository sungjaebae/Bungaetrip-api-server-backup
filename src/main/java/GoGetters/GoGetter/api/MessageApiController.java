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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
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
        List<Message> messages=messageService.findAllMessages(receiverId);
        log.debug("Log MessageController | get | /messages?receiverId | request: {} | message data : {}",receiverId,messages);
        List<MessageDto> collect = messages.stream().map(m ->
                new MessageDto(m.getId(),new MemberReturnDto(m.getSender().getMember()), m.getContent(),
                m.getCreatedAt())).collect(Collectors.toList());
        log.info("Log MessageController | get | /messages?receiverId | request: {} | response data : {}",receiverId,collect);

        return ResponseUtil.successResponse(HttpStatus.OK, collect);

    }
    //message 내용 조회 api
    @GetMapping(value = "/messages",params = "messageId")
    public ResponseEntity readMessage(@RequestParam("messageId")Long messageId){
        Message message = messageService.findMessage(messageId);
        Sender sender=message.getSender();
        log.debug("Log MessageController | get | /messages?messageId | messageId: {}, senderId: {}",messageId,sender.getId() );

        MessageDto messageDto=new MessageDto(message.getId(),new MemberReturnDto(sender.getMember()),
                message.getContent(),message.getCreatedAt());
        log.info("Log MessageController | get | /messages?messageId | request: {} | response data : {}",messageId,messageDto);

        return ResponseUtil.successResponse(HttpStatus.OK, messageDto);
    }
    //메시지 작성 api
    @PostMapping("/messages")
    public ResponseEntity createMessage(@RequestBody MessageRequest messageRequest){
        //senderId, receiverId를 통해 sender,receiver 찾기
        Sender sender= memberService.findSender(messageRequest.getSenderId());
        Receiver receiver= memberService.findReceiver(messageRequest.getReceiverId());
        log.debug("Log MessageController | get | /messages?receiverId | request: {}",messageRequest);
        log.debug("Log MessageController | get | /messages?receiverId | senderId: {}, receiverId: {}", sender.getId(),receiver.getId());


        //메시지 작성
        Message message=new Message(messageRequest.getContent());
        Long messageId= messageService.send(sender,receiver,message);
        log.info("Log MessageController | post | /messages | written message id: {}",messageId);

        Map<String,Long> ret=new HashMap<>();
        ret.put("messageId",messageId);

        log.info("Log MessageController | post | /messages | request: {} | response data : {}",messageRequest,ret);

        return ResponseUtil.successResponse(HttpStatus.OK, ret);

    }



}
