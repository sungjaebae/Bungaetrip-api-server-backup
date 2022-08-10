package GoGetters.GoGetter.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class FcmMessage {
    private Boolean validateOnly;
    private FcmInnerMessage message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class FcmInnerMessage {
        private Notification notification;
        private String token;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Notification{
        private String title;
        private String body;
        private String image;
    }

}
