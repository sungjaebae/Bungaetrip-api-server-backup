//package GoGetters.GoGetter.api;
//
//import GoGetters.GoGetter.dto.Result;
//import GoGetters.GoGetter.file.S3Uploader;
//import lombok.RequiredArgsConstructor;
//import org.apache.http.HttpStatus;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.imageio.IIOException;
//import java.io.IOException;
//
//@RestController
//@RequiredArgsConstructor
//public class S3Controller {
//    private final S3Uploader s3Uploader;
//
//    @PostMapping("/user/profile/image")
//    public String createProfileImage(
////            @RequestParam("memberId")Long memberId,
//            @RequestParam("image") MultipartFile multipartFile) throws IOException {
//        s3Uploader.upload(multipartFile, "static");
//        return "test";
//    }
//}
