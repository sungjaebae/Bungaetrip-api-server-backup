package GoGetters.GoGetter.dto.content;

import lombok.Data;

import java.util.List;

@Data
public class ContentListPeopleLikeResponse {
    private String contentListTitle;

    private List<ContentQueryResponse> contentList;

    public ContentListPeopleLikeResponse(String contentListTitle, List<ContentQueryResponse> contents) {
        this.contentListTitle=contentListTitle;
        this.contentList=contents;

    }
}
