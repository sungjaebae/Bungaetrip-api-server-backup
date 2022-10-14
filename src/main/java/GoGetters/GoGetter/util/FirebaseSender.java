package GoGetters.GoGetter.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class FirebaseSender {
    private RestTemplate restTemplate=new RestTemplate();
//    비공개 키
//    경로
    private final String CONFIG_PATH = "firebase/firebase-key.json";
//    토큰 발급
//    URL
    private final String AUTH_URL = "https://www.googleapis.com/auth/cloud-platform";
//    엔드포인트 URL
    private final String SEND_URL = "https://fcm.googleapis.com/v1/projects/gogetter-project/messages:send";

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
            private Data data;
        }

        @Getter
        @Builder
        @AllArgsConstructor
        private static class Notification {
            private String title;
            private String body;
        }

        @Getter
        @Builder
        @AllArgsConstructor
        private static class Data{
            private String type;
            private String messageId;
        }
    }

    private PushPayload makeMessageBody(String targetToken, String title, String body,String messageId) {
        return PushPayload.builder()
                .message(PushPayload.PushMessage.builder()
                        .token(targetToken)
                        .notification(PushPayload.Notification.builder()
                                .title(title)
                                .body(body)
                                .build())
                        .data(PushPayload.Data.builder()
                                .type("receiveMessage")
                                .messageId(messageId)
                                .build())
                        .build()).validateOnly(false).build();
    }

    public void pushBrowserSend(String token, String title, String body,String messageId) throws IOException {
//        발송 API 호출
        log.info("print push message info token:{}, title:{},body:{}",token,title,body);

        ObjectMapper objectMapper=new ObjectMapper();
        String message = objectMapper.writeValueAsString(makeMessageBody(token, title, body,messageId));
        log.info("print message body json: {}",message);
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,
                okhttp3.MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(SEND_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
//                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        client.newCall(request).enqueue(
                new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                        System.out.printf("print fcm message");
                        log.error("print fcm message error : {}", e.getMessage());
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        log.error("print fcm message response {}",response.body().string());

                    }
                }
        );

    }
}