package GoGetters.GoGetter.dto.article;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "게시글 수정 요청")
public class UpdateArticleRequest {

    @Schema(description = "게시글 번호")
    private Long articleId;

    @Schema(description = "출발지")
    private String departure;

    @Schema(description = "도착지")
    private String destination;

    @Schema(description = "현재 인원")
    private Integer currentParticipants;

    @Schema(description = "약속 날짜",example = "yyyy-MM-dd",format = "yyyy-mm-ss")
    private LocalDate date;

    @Schema(description = "약속 시간",type = "string",example = "HH:mm:ss",format = "HH:mm:ss")
    private LocalTime time;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "게시글 내용")
    private String content;

    @Schema(description = "출발지 위도")
    private Double departureLongitude;

    @Schema(description = "출발지 경도")
    private Double departureLatitude;

    @Schema(description = "도착지 위도")
    private Double destinationLongitude;

    @Schema(description = "도착지 경도")
    private Double destinationLatitude;
}
