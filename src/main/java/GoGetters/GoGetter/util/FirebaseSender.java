package GoGetters.GoGetter.util;

import GoGetters.GoGetter.domain.FcmMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.auth.oauth2.GoogleCredentials;
import io.lettuce.core.api.push.PushMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FirebaseSender {
    private final RestTemplate restTemplate;
//    비공개 키
//    경로
    private final String CONFIG_PATH = "firebase/firebase-key.json";
//    토큰 발급
//    URL
    private final String AUTH_URL = "https:www.googleapis.com/auth/cloud-platform";
//    엔드포인트 URL
    private final String SEND_URL = "https:fcm.googleapis.com/v1/projects/프로젝트ID/messages:send";

    private String getAccessToken() throws IOException {
//        토큰 발급
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource(CONFIG_PATH).getInputStream()).createScoped(List.of(AUTH_URL));
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    private static class PushPayload {
//        API 호출시
//        Body에 보낼
//        객체
        private boolean validateOnly;
        private PushMessage message;

        @Getter
        @Builder
        @AllArgsConstructor
        private static class PushMessage {
            private String token;
            private Notification notification;
        }

        @Getter
        @Builder
        @AllArgsConstructor
        private static class Notification {
            private String title;
            private String body;
            private String image;
        }
    }

    private PushPayload getBody(String targetToken, String title, String body) {
        return PushPayload.builder()
                .message(PushPayload.PushMessage.builder()
                        .token(targetToken)
                        .notification(PushPayload.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build())
                        .build()).validateOnly(false).build();
    }

    public void pushBrowserSend(String token, String title, String body) throws IOException {
//        발송 API 호출
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken());
        final HttpEntity<Object> entity = new HttpEntity<>(getBody(token, title, body), headers);
        final ResponseEntity<String> response = restTemplate.exchange(SEND_URL, HttpMethod.POST, entity, String.class);
        final HttpStatus status = response.getStatusCode();
        final String responseBody = response.getBody();
        if (status.equals(HttpStatus.OK)) {
//            발송 API 호출 성공
        } else {
//            발송 API 호출 실패
        }
    }

}
//
//package GoGetters.GoGetter.util;
//
//        import GoGetters.GoGetter.domain.FcmMessage;
//        import com.fasterxml.jackson.core.JsonProcessingException;
//        import com.google.auth.oauth2.GoogleCredentials;
//        import com.google.common.net.HttpHeaders;
//        import io.lettuce.core.api.push.PushMessage;
//        import lombok.AllArgsConstructor;
//        import lombok.Builder;
//        import lombok.Getter;
//        import lombok.RequiredArgsConstructor;
//        import okhttp3.*;
//        import org.springframework.core.io.ClassPathResource;
//        import org.springframework.web.client.RestTemplate;
//
//        import java.io.IOException;
//        import java.util.Collections;
//        import java.util.List;
//
//@RequiredArgsConstructor
//public class FirebaseSender {
//    private final RestTemplate restTemplate;
//    //    비공개 키
////    경로
//    private final String CONFIG_PATH = "firebase/firebase-key.json";
//    //    토큰 발급
////    URL
//    private final String API_URL = "https://www.googleapis.com/auth/cloud-platform";
//    //    엔드포인트 URL
//    private final String SEND_URL = "https://fcm.googleapis.com/v1/projects/프로젝트ID/messages:send";
//
//
//
//    @Getter
//    @Builder
//    @AllArgsConstructor
//    private static class PushPayload {
//        //        API 호출시
////        Body에 보낼
////        객체
//        private Boolean validateOnly;
//        private PushMessage message;
//
//        @Getter
//        @Builder
//        @AllArgsConstructor
//
//        private static class Message {
//            private String token;
//            private Notification notification;
//        }
//
//        @Getter
//        @Builder
//        private static class Notification {
//            private String title;
//            private String body;
//            private String image;
//        }
//    }
//
////    private PushPayload getBody(String to, String title, String body) {
////        return PushPayload.builder().validateOnly(false).message(PushPayload.Message.builder().token(to).notification(PushPayload.Notification.builder().title(title).body(body).image(null).build()).build()).build();
////    }
//
//    public void pushBrowserSend(String targetToken, String title, String body) throws IOException {
////        발송 API 호출
//        String message = makeMessage(targetToken, title, body);
//
//        OkHttpClient client = new OkHttpClient();
//        RequestBody requestBody = RequestBody.create(message,
//                okhttp3.MediaType.get("application/json; charset=utf-8"));
//        Request request = new Request.Builder()
//                .url(API_URL)
//                .post(requestBody)
//                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
//                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
//                .build();
//
//        Response response = client.newCall(request).execute();
//
//        System.out.println(response.body().string());
//    }
//    private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
//        FcmMessage fcmMessage = FcmMessage.builder()
//                .message(FcmMessage.FcmInnerMessage.builder()
//                        .token(targetToken)
//                        .notification(FcmMessage.Notification.builder()
//                                .title(title)
//                                .body(body)
//                                .image(null)
//                                .build()
//                        ).build()).validateOnly(false).build();
//
//        return objectMapper.writeValueAsString(fcmMessage);
//    }
//    private String getAccessToken() throws IOException, IOException {
////        토큰 발급
//        GoogleCredentials googleCredentials = GoogleCredentials.
//                fromStream(new ClassPathResource(CONFIG_PATH)
//                        .getInputStream()).createScoped(List.of(AUTH_URL));
//        googleCredentials.refreshIfExpired();
//        return googleCredentials.getAccessToken().getTokenValue();
//    }
//
//}