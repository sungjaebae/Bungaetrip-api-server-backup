package GoGetters.GoGetter.repository;

import GoGetters.GoGetter.domain.Article;
import GoGetters.GoGetter.domain.ArticleStatus;
import GoGetters.GoGetter.dto.ArticleDto;
import GoGetters.GoGetter.dto.RequestDto.UpdateArticleRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class ArticleRepository {
    @PersistenceContext
    private EntityManager em;

    public  Long save(Article article) {
        em.persist(article);
        return article.getId();
    }

    public Article findArticle(Long articleId) throws InterruptedException {
        String query = "select a from Article a " +
//                "join fetch a.writer w " +
                "where a.id=:articleId";
        Article article=em.createQuery(query,Article.class)
                .setParameter("articleId",articleId).getSingleResult();
//        Article article= em.find(Article.class,articleId);
        if (article.getStatus() == ArticleStatus.DELETE) {
            throw new InterruptedException("해당 글은 삭제되었습니다.");
        }
        return article;
    }

    public List<Article> findAllArticles(){
        String query="select a from Article a " +
                "join fetch a.writer order by a.createdAt desc";
        return em.createQuery(query,Article.class).getResultList();
    }

    public List<Article> findCreateArticles(){
        String query="select a from Article a"
                +" join fetch a.writer"
                +" where a.status=:status order by a.createdAt desc";
        log.debug("findCreateArticles query : {}",query);
        return em.createQuery(query,Article.class)
                .setParameter("status",ArticleStatus.CREATE)
                .getResultList();
    }

    //글 삭제하기
    public Long deleteArticle(Long articleId) throws InterruptedException {
        Article article=this.findArticle(articleId);
        article.changeArticleStatus(ArticleStatus.DELETE);
        return article.getId();
    }

    //글 수정
    public Long modifyArticle(UpdateArticleRequest fixes) throws InterruptedException {
        Article article=this.findArticle(fixes.getArticleId());

        article.modifyArticle(fixes);
        return article.getId();
    }

    //글 검색
    public List<Article> findArticlesByKeyword(String keyword){
        String likeVariable="'%"+keyword+"%'";
        String query="select a from Article a where a.status=:status and (a.title like "+likeVariable
                +" or a.content like "+likeVariable
                +" or a.departure like "+likeVariable
                +" or a.destination like "+likeVariable
                +") order by a.createdAt desc";

        return em.createQuery(query, Article.class).setParameter("status",ArticleStatus.CREATE).getResultList();
    }


    public List<Article> findArticlesByMemberId(Long memberId) {
        String query="select a from Article a join fetch a.writer w " +
                "where w.id=:memberId " +
                "and a.status=:status order by a.createdAt desc";
        log.debug("Log findArticle query: {}", query);

        return em.createQuery(query, Article.class)
                .setParameter("memberId", memberId)
                .setParameter("status", ArticleStatus.CREATE)
                .getResultList();
    }

    public List<Article> sortByMeetingDate() {
        String query = "select a from Article a join fetch a.writer " +
                "where a.status=:status " +
                "order by a.date, a.time";
        log.debug("Article Repo sort query:{}",query);
        return em.createQuery(query, Article.class)
                .setParameter("status", ArticleStatus.CREATE)
                .getResultList();
    }
}
