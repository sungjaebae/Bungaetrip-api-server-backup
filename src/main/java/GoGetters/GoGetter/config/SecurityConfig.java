//package GoGetters.GoGetter.config;
//
//import GoGetters.GoGetter.filter.JwtFilter;
//import com.google.firebase.auth.FirebaseAuth;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.HttpStatusEntryPoint;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
//public class SecurityConfig {
//    private final UserDetailsService userDetailsService;
//
//    private final FirebaseAuth firebaseAuth;
//
////    @Override
////    public void configure(HttpSecurity http) throws Exception {
////        http.authorizeRequests()
////                .anyRequest().authenticated().and()
////                .addFilterBefore(new FirebaseTokenFilter(userDetailsService, firebaseAuth),
////                        UsernamePasswordAuthenticationFilter.class)
////                .exceptionHandling()
////                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
////    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws
//            Exception {
//        http.authorizeRequests()
//                .anyRequest().authenticated().and()
//                .addFilterBefore(new JwtFilter(userDetailsService, firebaseAuth),
//                        UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling()
//                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
//        return http.build();
//    }
////    @Override
////    public void configure(WebSecurity web) throws Exception {
////        // 회원가입, 메인페이지, 리소스
////        web.ignoring().antMatchers(HttpMethod.POST, "/users")
////                .antMatchers("/")
////                .antMatchers("/resources/**");
////    }
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers(HttpMethod.POST, "/users")
//                .antMatchers("/")
//                .antMatchers("/resources/**");
//    }
//}
package GoGetters.GoGetter.config;


import GoGetters.GoGetter.filter.JwtRequestFilter;
import GoGetters.GoGetter.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {
    private final MemberService memberService;

    private final JwtRequestFilter jwtRequestFilter;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws
            Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/users/signup").permitAll()
                .antMatchers("/users/login").permitAll()
                .antMatchers("/h2-console/**").permitAll()
//                .antMatchers("/user/verify/**").permitAll()
//                .antMatchers("/oauth/**").permitAll()
                .antMatchers("/articles").hasRole("USER")
//                .antMatchers("/test/admin").hasRole("ADMIN")
                .anyRequest().authenticated();

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }


    @Bean// ignore check swagger resource
    public WebSecurityCustomizer webSecurityCustomizer() {

            return (web) -> web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**");
    }

//    @Bean

//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

}