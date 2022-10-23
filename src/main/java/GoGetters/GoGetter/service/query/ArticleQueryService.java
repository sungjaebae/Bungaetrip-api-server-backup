package GoGetters.GoGetter.service.query;

import GoGetters.GoGetter.domain.article.Article;
import GoGetters.GoGetter.domain.article.ArticleType;
import GoGetters.GoGetter.domain.content.Content;
import GoGetters.GoGetter.domain.member.Member;
import GoGetters.GoGetter.dto.article.CreateArticleRequest;
import GoGetters.GoGetter.repository.ArticleRepository;
import GoGetters.GoGetter.repository.ContentRepository;
import GoGetters.GoGetter.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleQueryService {
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;
    private final ContentRepository contentRepository;

    @Transactional
    public Article createQueryArticle(CreateArticleRequest createArticleRequest) {
        Member member = memberRepository.findOne(createArticleRequest.getMemberId());
        Content destinationContent = contentRepository.findOne(createArticleRequest.getDestinationContentId());
        Article article = new Article(member, ArticleType.valueOf(createArticleRequest.getArticleType()),
                createArticleRequest.getDeparture()
                , createArticleRequest.getDestination(), destinationContent,
                createArticleRequest.getDate(), createArticleRequest.getTime(),
                createArticleRequest.getCurrentParticipants(),
                createArticleRequest.getTitle(), createArticleRequest.getContent(),
                createArticleRequest.getDepartureLongitude(), createArticleRequest.getDepartureLatitude(),
                createArticleRequest.getDestinationLongitude(), createArticleRequest.getDestinationLatitude()
        );

        Long writeId = articleRepository.save(article);
        return articleRepository.findArticle(writeId);
    }

}
