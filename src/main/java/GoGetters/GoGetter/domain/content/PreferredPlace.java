package GoGetters.GoGetter.domain.content;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
public class PreferredPlace {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String place;

    public PreferredPlace(String place) {
        this.place = place;
    }
}
