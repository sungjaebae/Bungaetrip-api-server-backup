package GoGetters.GoGetter.exception;

import GoGetters.GoGetter.MessageResource;
import GoGetters.GoGetter.exception.Article.InvalidSortTypeException;
import GoGetters.GoGetter.exception.Article.NoSuchArticleException;
import GoGetters.GoGetter.exception.Member.MemberAlreadyExistException;
import GoGetters.GoGetter.exception.Member.NoSuchMemberException;
import GoGetters.GoGetter.exception.Message.NoSuchMessageException;
import GoGetters.GoGetter.exception.report.AlreadyReportException;
import GoGetters.GoGetter.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //  ===================== Article =====================
    @ExceptionHandler(NoSuchArticleException.class)
    public ResponseEntity handleNoArticleException(final NoSuchArticleException e) {
        log.error("Handle no article exception : {}", e.getMessage());
        return ResponseUtil.errorResponse(MessageResource.articleNotExist, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidSortTypeException.class)
    public ResponseEntity handleInvalidArticleTypeException(final InvalidSortTypeException e) {
        log.error("Handle  invalid article sort type exception : {}", e.getMessage());
        return ResponseUtil.errorResponse(MessageResource.invalidArticleSortType, HttpStatus.BAD_REQUEST);
    }

    //  ===================== Message ====================
    @ExceptionHandler(NoSuchMessageException.class)
    public ResponseEntity handleNoSuchMessageException(final NoSuchMessageException e) {
        log.error("Handle no message exception", e.getMessage());
        return ResponseUtil.errorResponse(MessageResource.memberNotExist, HttpStatus.NOT_FOUND);
    }

    //  ====================== Member =====================
    @ExceptionHandler(NoSuchMemberException.class)
    public ResponseEntity handleNoMemberException(final NoSuchMemberException e) {
        log.error("Handle no member exception : {}", e.getMessage());
        return ResponseUtil.errorResponse(MessageResource.memberNotExist, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity handleUsernameNotFoundException(final UsernameNotFoundException e) {
        log.error("Handle username not found exception", e.getMessage());
        return ResponseUtil.errorResponse(MessageResource.memberNotExist, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MemberAlreadyExistException.class)
    public ResponseEntity handleMemberAlreadyExistException(final MemberAlreadyExistException e) {
        log.error("Handle memberAlreadyExist exception", e.getMessage());
        return ResponseUtil.errorResponse(MessageResource.alreadyMemberExist, HttpStatus.BAD_REQUEST);
    }

    //    ====================== Report -===================
    @ExceptionHandler(AlreadyReportException.class)
    public ResponseEntity handleAlreadyReportException(final AlreadyReportException e) {
        log.error("Handle already report exception", e.getMessage());
        return ResponseUtil.errorResponse(MessageResource.alreadyReportedMemberExist, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(final IllegalArgumentException e) {
        log.error("Handle already report exception", e.getMessage());
        return ResponseUtil.errorResponse(MessageResource.illegalArgument, HttpStatus.BAD_REQUEST);

    }
}
