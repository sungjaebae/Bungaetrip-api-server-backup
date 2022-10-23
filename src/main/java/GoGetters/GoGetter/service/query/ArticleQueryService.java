package GoGetters.GoGetter.service.query;

import GoGetters.GoGetter.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional()
public class ArticleQueryService {
    private final ArticleRepository articleRepository;

}
