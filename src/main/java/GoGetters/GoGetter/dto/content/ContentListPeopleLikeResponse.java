package GoGetters.GoGetter.dto.content;

import GoGetters.GoGetter.domain.content.Content;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ContentListPeopleLikeResponse {
    private List<ContentListResponse> restaurantList;
    private List<ContentListResponse> cafeList;
    private List<ContentListResponse> attractionList;

    public ContentListPeopleLikeResponse(List<Content> restaurants, List<Content> cafes,
                                         List<Content> attractions) {
        this.restaurantList= restaurants.stream().map(r -> new ContentListResponse(r)).collect(Collectors.toList());
        this.cafeList= cafes.stream().map(c -> new ContentListResponse(c)).collect(Collectors.toList());
        this.attractionList= attractions.stream().map(a -> new ContentListResponse(a)).collect(Collectors.toList());
    }
}
