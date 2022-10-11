package GoGetters.GoGetter.dto.content;

import GoGetters.GoGetter.domain.content.Content;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ContentListPeopleLikeResponse {
    private String contentListTitle;

    private List<ContentResponse> contentList;

    public ContentListPeopleLikeResponse(String contentListTitle, List<Content> contents) {
        this.contentListTitle=contentListTitle;
        this.contentList=contents.stream()
                .map(content -> new ContentResponse(content))
                .collect(Collectors.toList());

    }
}
