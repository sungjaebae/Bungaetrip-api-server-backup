package GoGetters.GoGetter.dto.image;

import GoGetters.GoGetter.domain.Image.Image;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

@Data
@NoArgsConstructor
public class ContentImageDto {
    private Long imageId;
    private String imageUrl;

    @JsonIgnore
    private Long contentId;

    public ContentImageDto(Image image) {
        this.imageId=image.getId();
        this.imageUrl=image.getImageUrl();
    }

    public ContentImageDto(Long contentId, Long imageId, String imageUrl) {
        this.contentId=contentId;
        this.imageId=imageId;
        this.imageUrl=imageUrl;
    }
}
