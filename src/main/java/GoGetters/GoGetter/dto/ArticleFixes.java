package GoGetters.GoGetter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@Getter
public class ArticleFixes {
    private String departure;

    private String destination;

    private LocalDate date;

    private LocalTime time;

    private Integer currentParticipants;

    private Integer totalParticipants;

    private String title;

    private String content;
}
