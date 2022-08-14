package GoGetters.GoGetter.api;

import GoGetters.GoGetter.MessageResource;
import GoGetters.GoGetter.domain.message.Member;
import GoGetters.GoGetter.dto.member.CanMemberGenerate;
import GoGetters.GoGetter.dto.member.MemberInfoReturn;
import GoGetters.GoGetter.dto.member.UpdateMemberRequest;
import GoGetters.GoGetter.exception.Member.InvalidUpdateMemberInfoException;
import GoGetters.GoGetter.exception.Member.NoSuchMemberException;
import GoGetters.GoGetter.service.MemberService;
import GoGetters.GoGetter.util.BlobStorage;
import GoGetters.GoGetter.util.JwtUtil;
import GoGetters.GoGetter.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApiController {
    private final JwtUtil jwtUtil;
    private final MemberService memberService;

    private final BlobStorage blobStorage;

    @GetMapping(value = "/{memberId}")
    public ResponseEntity readMemberById(@PathVariable("memberId") Long memberId) {
        Member member = memberService.findOne(memberId);
        MemberInfoReturn memberResponse=new MemberInfoReturn(member);
        return ResponseUtil.successResponse(HttpStatus.OK,memberResponse);
    }
    @PatchMapping(value = "/myInfo", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity updateMemberInfo(@RequestPart UpdateMemberRequest request,
                                           @RequestPart(required = false) MultipartFile imgFile) throws IOException {
        if (!request.getGender().equals("MALE") && !request.getGender().equals("FEMALE")) {
            throw new InvalidUpdateMemberInfoException(MessageResource.invalidMemberRequestForm);
        }
        String imageBlobUrl = null;
        Long updatedId = null;
        if (imgFile != null) {
            // 1. 기존 프로필이 존재하는지 확인하기 위해 멤버 조회
            Member member = memberService.findOne(request.getMemberId());
            log.info("member imageUrl : {}",member.getProfileUrl());
            // 2. 기존 프로필이 존재한다면 blob 삭제
            if(member.getProfileFileName()!=null)
                blobStorage.deleteBlob(member.getProfileFileName());

            // 3. blob Container 에 프로필 업로드
            String blobFileName = blobStorage.uploadFile(imgFile);

            // 4. blob url 및 fileName 저장
            updatedId = memberService.updateMyInfo(request, blobStorage.getFileUrl(blobFileName),blobFileName);
        }
        else {
            updatedId = memberService.updateMyInfo(request);
        }
        return ResponseUtil.successResponse(HttpStatus.OK, updatedId);
    }

    @GetMapping(value = "/username")
    public ResponseEntity validateUsername(@RequestParam("username") String username) {
        log.debug("Log /member/username");
        List<Member> membersByUsername= memberService.findMemberByUsername(username);
        log.debug("Log /member/username size : {}",membersByUsername.size());
        Boolean isAvailable=true;
        if (membersByUsername.size() != 0)
            isAvailable=false;
        return ResponseUtil.successResponse(HttpStatus.OK,new CanMemberGenerate(username,isAvailable));
    }

    @GetMapping(value = "/email")
    public ResponseEntity validateEmail(@RequestParam("email") String email) {
        log.debug("Log /member/email");
        List<Member> membersByEmail= memberService.findMemberByEmail(email);
        log.debug("Log /member/email size : {}",membersByEmail.size());
        Boolean isAvailable=true;
        if (membersByEmail.size() != 0)
            isAvailable=false;
        return ResponseUtil.successResponse(HttpStatus.OK,new CanMemberGenerate(email,isAvailable));
    }

    @GetMapping(value = "")
    public ResponseEntity readMember(
            @RequestHeader("Authorization") String authorization) {
        log.debug("JWT authorization : {}",authorization);
        String token = authorization.substring("Bearer ".length());
        log.debug("JWT token content : {}", token);
        String username = jwtUtil.getUsername(token);
        log.debug("JWT token claim username : {}",username);
        List<Member> memberByUsername = memberService.findMemberByUsername(username);
        if (memberByUsername.size() == 0) {
            throw new NoSuchMemberException(MessageResource.memberNotExist);
        }
        Member member = memberByUsername.get(0);
        return ResponseUtil.successResponse(HttpStatus.OK, new MemberInfoReturn(member));

    }



}