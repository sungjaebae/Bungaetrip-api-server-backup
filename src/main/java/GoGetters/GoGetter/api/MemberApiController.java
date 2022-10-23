package GoGetters.GoGetter.api;

import GoGetters.GoGetter.MessageResource;
import GoGetters.GoGetter.domain.member.Member;
import GoGetters.GoGetter.dto.member.CanMemberGenerate;
import GoGetters.GoGetter.dto.member.MemberInfoReturn;
import GoGetters.GoGetter.dto.member.UpdateMemberRequest;
import GoGetters.GoGetter.exception.Member.InvalidUpdateMemberInfoException;
import GoGetters.GoGetter.service.MemberService;
import GoGetters.GoGetter.service.query.MemberQueryService;
import GoGetters.GoGetter.util.BlobStorage;
import GoGetters.GoGetter.util.JwtUtil;
import GoGetters.GoGetter.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
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
    private final MemberQueryService memberQueryService;

    private final BlobStorage blobStorage;

    @GetMapping(value = "/{memberId}")
    @Operation(summary = "회원 정보 조회 API",description = "회원 번호를 통해 회원의 정보를 조회합니다. 회원 번호, 아이디, 이메일, 닉네임, " +
            "나이를 JSON 형태로 반환합니다")
    public ResponseEntity readMemberById(@PathVariable("memberId") Long memberId) {
        MemberInfoReturn member = memberQueryService.readMemberById(memberId);
        return ResponseUtil.successResponse(HttpStatus.OK,member);
    }
    @PatchMapping(value = "/myInfo", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "회원 정보 수정 API", description = "회원 번호, 닉네임, 나이, 성별, 자기 소개 그리고 프로필 사진을 입력받아 사용자 정보를" +
            " 추가합니다")
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
    @Operation(summary = "회원 아이디 사용가능 여부 조회 API",description = "회원가입을 할 때 입력한 아이디가 사용 가능한지 판별한다")
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
    @Operation(summary ="이메일 사용가능 여부 조회 API",description = "회원가입을 할 때 입력한 이메일이 사용 가능한지 판별한다")
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
    @Operation(summary = "토큰을 통해 회원 정보 조회 API",description = "사용자가 가지고 있는 jwt 토큰을 통해" +
            " 회원 번호를 파싱하고, 이를 사용하여 회원 정보를 JSON 형태로 반환한다")
    public ResponseEntity readMember(
            @RequestHeader("Authorization") String authorization) {
        MemberInfoReturn member = memberQueryService.readMember(authorization);
        return ResponseUtil.successResponse(HttpStatus.OK, member);

    }



}