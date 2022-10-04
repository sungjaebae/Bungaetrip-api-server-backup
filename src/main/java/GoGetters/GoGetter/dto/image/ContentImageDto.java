package GoGetters.GoGetter.dto.image;

import GoGetters.GoGetter.domain.Image.Image;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContentImageDto {
    private Long imageId;
    private String imageUrl;

    public ContentImageDto(Image image) {
        this.imageId=image.getId();
        this.imageUrl=image.getImageUrl();
    }
}
