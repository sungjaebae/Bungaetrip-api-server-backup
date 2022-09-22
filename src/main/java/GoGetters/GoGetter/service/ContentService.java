package GoGetters.GoGetter.service;

import GoGetters.GoGetter.domain.content.Content;
import GoGetters.GoGetter.domain.content.ContentType;
import GoGetters.GoGetter.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;



    public Content findOne(Long contentId) {
        return contentRepository.findOne(contentId);
    }

    public List<Content> findAllBySearchKeyword(String searchKeyword) {
        return contentRepository.findAllBySearchKeyword(searchKeyword);
    }

    public List<Content> findPlaceInAreaByFilter(Double left, Double right, Double top, Double bottom, ContentType filter) {
        return contentRepository.findAllByLocationAndFilter(left, right, top, bottom, filter);
    }
}
