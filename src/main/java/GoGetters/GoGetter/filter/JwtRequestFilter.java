package GoGetters.GoGetter.filter;


import GoGetters.GoGetter.MessageResource;
import GoGetters.GoGetter.domain.UserRole;
import GoGetters.GoGetter.service.MemberService;
import GoGetters.GoGetter.util.CookieUtil;
import GoGetters.GoGetter.util.JwtUtil;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final MemberService memberService;


    private final JwtUtil jwtUtil;

    private final CookieUtil cookieUtil;

//    private final FirebaseAuth firebaseAuth;
    private final UserDetailsService userDetailsService;

    public JwtRequestFilter(@Lazy MemberService memberService
            , @Lazy UserDetailsService userDetailsService
            , JwtUtil jwtUtil
            , CookieUtil cookieUtil
//            , FirebaseAuth firebaseAuth
    ) {
        this.memberService = memberService;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.cookieUtil = cookieUtil;
//        this.firebaseAuth = firebaseAuth;
    }

//    private final RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse
            , FilterChain filterChain) throws ServletException, IOException {
        //자체 jwt 부분


        //firebase 토큰 확인 및 처리
//        SetUpFirebaseAdmin(httpServletRequest,httpServletResponse);
        log.debug("Init jwt request filter");
        //로그인을 한 후 jwt 발급하기 위한 설정
        String header = httpServletRequest.getHeader("Authorization");
        log.debug("Header authorization value : {}",header);
        if (header != null && header.startsWith("Bearer ")){
            try {
                validateJwt(httpServletRequest,header);
            } catch (InterruptedException e) {
                log.error(MessageResource.invalidToken);
                throw new RuntimeException(MessageResource.invalidToken,e);
            }
        }


        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

//    private void SetUpFirebaseAdmin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
//        FirebaseToken decodedToken;
//        String header = httpServletRequest.getHeader("Authorization");
//
//        if (header != null && header.startsWith("Bearer ")) {
//            String token = header.substring(7);
//            try {
//                System.out.println("start decodedToken");
//                decodedToken = firebaseAuth.verifyIdToken(token);
//                System.out.println(decodedToken);
//            } catch (FirebaseAuthException e) {
//                setUnauthorizedResponse(httpServletResponse, "INVALID_TOKEN");
//                return;
//            }
//
//            try {
//                UserDetails user = userDetailsService.loadUserByUsername(decodedToken.getUid());
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                        user, null, user.getAuthorities());
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            } catch (NoSuchElementException e) {
//                setUnauthorizedResponse(httpServletResponse, "USER_NOT_FOUND");
//                return;
//            }
//        }
//    }

    private void validateJwt(HttpServletRequest httpServletRequest,String header)
            throws InterruptedException {


        String token = header.substring("Bearer ".length());
        log.debug("JWT token content :{}", token);
        String username = jwtUtil.getUsername(token);
        log.debug("JWT token claim username : {}",username);

        UserDetails userDetails = memberService.loadUserbyUsername(username);
        log.info("jwt load user :{}",userDetails.getUsername());
//                //토큰 검증 부분
        Boolean ret=jwtUtil.validateToken(token, userDetails);
        if (ret) {
            log.debug("jwt debug token ret:{}",ret);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

    }
//    private void setJwtRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
//        String username = null;
//        String role = null;
//        String jwt = null;
//        String refreshJwt = null;
//        String refreshUname = null;
//        final Cookie jwtToken = cookieUtil.getCookie(httpServletRequest, JwtUtil.ACCESS_TOKEN_NAME);
//        System.out.println(jwtToken);
//        try {
//            if (jwtToken != null) {
//
//                jwt = jwtToken.getValue();
//                System.out.println(jwt);
//                username = jwtUtil.getUsername(jwt);
//                System.out.println(username);
//
//                role = jwtUtil.getUserRole(jwt);
//                System.out.println("role");
//                System.out.println(role);
//            }
//            if (username != null & role != null) {
//                UserDetails userDetails = memberService.loadUserbyUsername(username);
//                System.out.println("authorities");
//                System.out.println(userDetails.getAuthorities());
//                Boolean valid = jwtUtil.validateToken(jwt, userDetails);
//                System.out.println(valid);
//                System.out.println(role);
//
//                if (valid && role.equals(UserRole.ROLE_USER.toString())) {
//                    System.out.println("jwt validate");
//                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
//                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//                }
//            }
//        } catch (ExpiredJwtException e) {
//            //refresh token 재발급 코드 부분
//
////            Cookie refreshToken = cookieUtil.getCookie(httpServletRequest,JwtUtil.REFRESH_TOKEN_NAME);
////            if(refreshToken!=null){
////                refreshJwt = refreshToken.getValue();
////            }
//        } catch (Exception e) {
//
//        }
//
////        try{
////            if(refreshJwt != null){
////                refreshUname = redisUtil.getData(refreshJwt);
////
////                if(refreshUname.equals(jwtUtil.getUsername(refreshJwt))){
////                    UserDetails userDetails = memberService.loadUserByUsername(refreshUname);
////                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
////                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
////                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
////
////                    Member member = new Member(refreshUname);
////                    String newToken =jwtUtil.generateToken(member);
////
////                    Cookie newAccessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME,newToken);
////                    httpServletResponse.addCookie(newAccessToken);
////                }
////            }
////        }catch(ExpiredJwtException e){
////
////        }
//    }
    private void setUnauthorizedResponse(HttpServletResponse response, String code) throws IOException {
        response.setStatus(HttpStatus.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"code\":\"" + code + "\"}");
    }
}