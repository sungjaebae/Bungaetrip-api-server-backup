package GoGetters.GoGetter.api;

import GoGetters.GoGetter.dto.content.PreferredPlaceRequest;
import GoGetters.GoGetter.repository.PreferredPlaceRepository;
import GoGetters.GoGetter.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/preference")
public class PreferenceApiController {
    private final PreferredPlaceRepository preferredPlaceRepository;


    @PostMapping(value = "")
    @Transactional
    public ResponseEntity createPreferredPlace(@RequestBody PreferredPlaceRequest request) {
        if (request.getPlace().equals("")) {
            throw new IllegalArgumentException();
        }
        Long placeId = preferredPlaceRepository.savePreferredPlace(request.getPlace());

        return ResponseUtil.successResponse(HttpStatus.CREATED,placeId);
    }


}
