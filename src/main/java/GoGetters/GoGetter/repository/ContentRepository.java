package GoGetters.GoGetter.repository;

import GoGetters.GoGetter.domain.content.Content;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Getter
public class ContentRepository {
    @PersistenceContext
    private EntityManager em;

    private Long save(Content content) {
        em.persist(content);
        return content.getId();
    }
}
