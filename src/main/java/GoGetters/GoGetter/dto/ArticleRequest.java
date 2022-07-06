package GoGetters.GoGetter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleRequest {
    private String departure;

    private String destination;

    private Integer currentParticipants;

    private Integer totalParticipants;
    private String title;

    private String content;
}
