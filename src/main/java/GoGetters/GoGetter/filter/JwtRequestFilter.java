package GoGetters.GoGetter.filter;


import GoGetters.GoGetter.domain.Member;
import GoGetters.GoGetter.domain.UserRole;
import GoGetters.GoGetter.service.MemberService;
import GoGetters.GoGetter.util.CookieUtil;
import GoGetters.GoGetter.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final MemberService memberService;


    private final JwtUtil jwtUtil;

    private final CookieUtil cookieUtil;

    public JwtRequestFilter(@Lazy MemberService memberService, JwtUtil jwtUtil, CookieUtil cookieUtil) {
        this.memberService=memberService;
        this.jwtUtil=jwtUtil;
        this.cookieUtil=cookieUtil;
    }

//    private final RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse
            , FilterChain filterChain) throws ServletException, IOException {

        final Cookie jwtToken = cookieUtil.getCookie(httpServletRequest,JwtUtil.ACCESS_TOKEN_NAME);

        String username = null;
        String role=null;
        String jwt = null;
        String refreshJwt = null;
        String refreshUname = null;
        try{
            if(jwtToken != null){

                jwt = jwtToken.getValue();
                System.out.println(jwt);
                username = jwtUtil.getUsername(jwt);
                System.out.println(username);

                role = jwtUtil.getUserRole(jwt);
                System.out.println("role");
                System.out.println(role);
            }
            if(username!=null &role!=null){
                UserDetails userDetails = memberService.findByUsername(username);
                System.out.println("authorities");
                System.out.println(userDetails.getAuthorities());
                Boolean valid = jwtUtil.validateToken(jwt, userDetails);
                System.out.println(valid);
                System.out.println(role);

                if(valid&&role.equals(UserRole.ROLE_USER.toString())){
                    System.out.println("jwt validate");
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }catch (ExpiredJwtException e){
            System.out.println("refresh token 필요");
//            Cookie refreshToken = cookieUtil.getCookie(httpServletRequest,JwtUtil.REFRESH_TOKEN_NAME);
//            if(refreshToken!=null){
//                refreshJwt = refreshToken.getValue();
//            }
        }catch(Exception e){

        }

//        try{
//            if(refreshJwt != null){
//                refreshUname = redisUtil.getData(refreshJwt);
//
//                if(refreshUname.equals(jwtUtil.getUsername(refreshJwt))){
//                    UserDetails userDetails = memberService.loadUserByUsername(refreshUname);
//                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
//                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//
//                    Member member = new Member(refreshUname);
//                    String newToken =jwtUtil.generateToken(member);
//
//                    Cookie newAccessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME,newToken);
//                    httpServletResponse.addCookie(newAccessToken);
//                }
//            }
//        }catch(ExpiredJwtException e){
//
//        }
        System.out.println("do filter done");
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}