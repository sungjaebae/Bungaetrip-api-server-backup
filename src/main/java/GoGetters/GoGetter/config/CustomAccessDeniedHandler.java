package GoGetters.GoGetter.config;

import GoGetters.GoGetter.domain.SecurityMember;
import GoGetters.GoGetter.domain.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse
            , AccessDeniedException e) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();

        httpServletResponse.setStatus(200);
        httpServletResponse.setContentType("application/json;charset=utf-8");
//        Response response = new Response("error","접근가능한 권한을 가지고 있지 않습니다.",null);
        Map<String, String> ret = new HashMap<>();
        ret.put("code","error");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityMember member = (SecurityMember)authentication.getPrincipal();
        Collection<GrantedAuthority> authorities = member.getAuthorities();

        if(hasRole(authorities, UserRole.ROLE_NOT_PERMITTED.name())){
            ret.put("messagge","인증을 받지 못했습니다");
        }

        PrintWriter out = httpServletResponse.getWriter();
        String jsonResponse = objectMapper.writeValueAsString(ret);
        out.print(jsonResponse);

    }

    private boolean hasRole(Collection<GrantedAuthority> authorites, String role){
        return authorites.contains(new SimpleGrantedAuthority(role));
    }

}