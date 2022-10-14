package GoGetters.GoGetter.repository;

import GoGetters.GoGetter.domain.content.PreferredPlace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class PreferredPlaceRepository {
    @PersistenceContext
    private EntityManager em;


    public Long savePreferredPlace(String place) {
        PreferredPlace preferredPlace = new PreferredPlace(place);
        em.persist(preferredPlace);
        return preferredPlace.getId();
    }
}
