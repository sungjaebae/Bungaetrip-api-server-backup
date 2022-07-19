package GoGetters.GoGetter.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRequest {
    private Long memberId;
    private String departure;

    private String destination;

    private Integer currentParticipants;

//    private Integer totalParticipants;

    private LocalDate date;

    private LocalTime time;

    private String title;

    private String content;
}
