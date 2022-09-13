package GoGetters.GoGetter.service;

import GoGetters.GoGetter.domain.content.Content;
import GoGetters.GoGetter.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;

    public List<Content> findAllByLocationAndFilter(Double left, Double right, Double top, Double bottom, String filter) {
        return null;
    }
}
