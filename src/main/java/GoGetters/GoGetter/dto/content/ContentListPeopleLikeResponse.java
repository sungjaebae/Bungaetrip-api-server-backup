package GoGetters.GoGetter.dto.content;

import GoGetters.GoGetter.domain.content.Content;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ContentListPeopleLikeResponse {
    private String contentListType;

    private List<ContentListResponse> contentList;

    public ContentListPeopleLikeResponse(String contentListType, List<Content> contents) {
        this.contentListType=contentListType;
        this.contentList=contents.stream()
                .map(content -> new ContentListResponse(content))
                .collect(Collectors.toList());

    }
}
