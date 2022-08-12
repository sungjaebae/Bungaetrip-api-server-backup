package GoGetters.GoGetter.api;

import GoGetters.GoGetter.domain.Message;
import GoGetters.GoGetter.domain.Receiver;
import GoGetters.GoGetter.domain.Sender;
import GoGetters.GoGetter.dto.messageDto.MessageDto;
import GoGetters.GoGetter.dto.messageDto.ReceivedMessageResponse;
import GoGetters.GoGetter.dto.messageDto.SentMessageResponse;
import GoGetters.GoGetter.dto.messageDto.MessageRequest;
import GoGetters.GoGetter.dto.memberDto.MemberInfoDto;
import GoGetters.GoGetter.service.MessageService;
import GoGetters.GoGetter.service.MemberService;
import GoGetters.GoGetter.util.FirebaseSender;
import GoGetters.GoGetter.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    private final FirebaseSender firebaseSender;
    //receiver_id 를 통해 message 목록 조회 api
    @GetMapping(value="/messages",params = "receiverId")
    public ResponseEntity listReceivedMessage(
                                      @RequestParam("receiverId") Long receiverId) {
        memberService.findOne(receiverId);

        List<Message> messages=messageService.findReceivedMessages(receiverId);
        List<ReceivedMessageResponse> collect = messages.stream().map(m ->
                new ReceivedMessageResponse(m)).collect(Collectors.toList());

        return ResponseUtil.successResponse(HttpStatus.OK, collect);

    }
    //message 내용 조회 api
    @GetMapping(value = "/messages",params = "messageId")
    public ResponseEntity readMessage(@RequestParam("messageId")Long messageId){
        Message message = messageService.findMessage(messageId);
        Sender sender=message.getSender();
        Receiver receiver=message.getReceiver();
        MessageDto messageDto=new MessageDto(message.getId(),new MemberInfoDto(sender.getMember()),
                new MemberInfoDto(receiver.getMember()),
                message.getContent(),message.getCreatedAt());
        return ResponseUtil.successResponse(HttpStatus.OK, messageDto);
    }
    //메시지 작성 api
    @PostMapping("/messages")
    public ResponseEntity createMessage(@RequestBody MessageRequest messageRequest) throws IOException {
        //senderId, receiverId를 통해 sender,receiver 찾기
        Sender sender= memberService.sender(messageRequest.getSenderId());
        Receiver receiver= memberService.receiver(messageRequest.getReceiverId());

        //메시지 작성
        Message message=new Message(messageRequest.getContent());
        Long messageId= messageService.send(sender,receiver,message);

        ////////////////////////////
        //fcm 에 메시지 보내기
        sendMessageToFCM(receiver,messageId);
        //////////////////////////////

        Map<String,Long> ret=new HashMap<>();
        ret.put("messageId",messageId);

        return ResponseUtil.successResponse(HttpStatus.OK, ret);

    }

    private void sendMessageToFCM(Receiver receiver,Long messageId) throws IOException {
        //1. 디바이스 정보 가져오기
        String fcmToken = receiver.getMember().getFcmToken();
        log.info("Fcm token {}",fcmToken);

        //2. fcm 에 전송하기
        String title = "알림";
        String body = "새 메시지가 도착했습니다";
        firebaseSender.pushBrowserSend(fcmToken,title,body,String.valueOf(messageId));
    }
    @GetMapping(value="/messages/sentMessages")
    public ResponseEntity listSentMessages(@RequestParam("memberId") Long memberId) {
        memberService.findOne(memberId);

        List<Message> sentMessages= messageService.findSentMessages(memberId);
        List<SentMessageResponse> collect = sentMessages.stream()
                .map(message -> new SentMessageResponse(message))
                .collect(Collectors.toList());
        return ResponseUtil.successResponse(HttpStatus.OK, collect);
    }


}
