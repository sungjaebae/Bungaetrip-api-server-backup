package GoGetters.GoGetter.api;

import GoGetters.GoGetter.domain.message.Message;
import GoGetters.GoGetter.domain.message.Receiver;
import GoGetters.GoGetter.domain.message.Sender;
import GoGetters.GoGetter.dto.message.MessageDto;
import GoGetters.GoGetter.dto.message.ReceivedMessageResponse;
import GoGetters.GoGetter.dto.message.SentMessageResponse;
import GoGetters.GoGetter.dto.message.MessageRequest;
import GoGetters.GoGetter.dto.member.MemberInfoDto;
import GoGetters.GoGetter.service.MessageService;
import GoGetters.GoGetter.service.MemberService;
import GoGetters.GoGetter.util.FirebaseSender;
import GoGetters.GoGetter.util.ResponseUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "받은 메세지 목록 조회 API",description = "회원의 번호로 " +
            "받아진 메세지를 모두 조회합니다. 메세지 번호, 보낸 사람에 대한 정보, 메세지 내용, " +
            "메세지 작성 시간 등의 정보를 가진 메세지들을 JSON 형태로 반환합니다")
    @ApiImplicitParam(name="receiverId",value = "메세지 받는 회원 번호",dataType = "Long",required = true,paramType = "query")
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
    @Operation(summary = "특정 메세지 조회 API",description = "메세지 번호를 통해 해당 메세지의 " +
            "내용을 조회합니다. 회원 번호, 메세지를 보낸 사람 및 받은 사람에 대한 정보, 메세지 내용 및 작성시간을 " +
            "JSON 형태로 반환합니다")
    @ApiImplicitParam(name="messageId",value = "메세지 번호",dataType = "Long",required = true,paramType = "query")
    public ResponseEntity readMessage(@RequestParam("messageId")Long messageId){
        Message message = messageService.findMessage(messageId);
        Sender sender=message.getSender();
        Receiver receiver=message.getReceiver();
        MessageDto messageDto=new MessageDto(message.getId(),new MemberInfoDto(sender.getMember()),
                new MemberInfoDto(receiver.getMember()),
                message.getContent(),message.getCreatedAt());
        return ResponseUtil.successResponse(HttpStatus.OK, messageDto);
    }
    //메세지 작성 api
    @PostMapping("/messages")
    @Operation(summary = "메세지 생성 API",description = "메세지를 보내는 회원의 번호 및 받는 회원의 번호 그리고" +
            "메세지 내용을 입력받아 정보들을 데이터베이스에 저장하고 메세지를 받을 사람에게 " +
            "알림을 보낸다")
    public ResponseEntity createMessage(@RequestBody MessageRequest messageRequest) throws IOException {
        //senderId, receiverId를 통해 sender,receiver 찾기
        Sender sender= memberService.sender(messageRequest.getSenderId());
        Receiver receiver= memberService.receiver(messageRequest.getReceiverId());

        //메세지 작성
        Message message=new Message(messageRequest.getContent());
        Long messageId= messageService.send(sender,receiver,message);

        ////////////////////////////
        //fcm 에 메세지 보내기
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
        String body = "새 메세지가 도착했습니다";
        firebaseSender.pushBrowserSend(fcmToken,title,body,String.valueOf(messageId));
    }
    @GetMapping(value="/messages/sentMessages")
    @Operation(summary = "보낸 메세지 목록 조회 API",description = "회원 번호를 통해" +
            "해당 회원이 보낸 메세지 목록을 JSON 형태로 반환한다")
    public ResponseEntity listSentMessages(@RequestParam("memberId") Long memberId) {
        memberService.findOne(memberId);

        List<Message> sentMessages= messageService.findSentMessages(memberId);
        List<SentMessageResponse> collect = sentMessages.stream()
                .map(message -> new SentMessageResponse(message))
                .collect(Collectors.toList());
        return ResponseUtil.successResponse(HttpStatus.OK, collect);
    }


}
