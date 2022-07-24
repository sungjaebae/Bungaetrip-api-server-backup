package GoGetters.GoGetter.util;


        import GoGetters.GoGetter.domain.Member;
        import GoGetters.GoGetter.domain.UserRole;
        import io.jsonwebtoken.Claims;
        import io.jsonwebtoken.ExpiredJwtException;
        import io.jsonwebtoken.Jwts;
        import io.jsonwebtoken.SignatureAlgorithm;
        import io.jsonwebtoken.security.Keys;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.security.core.userdetails.UserDetails;
        import org.springframework.stereotype.Component;

        import java.nio.charset.StandardCharsets;
        import java.security.Key;
        import java.util.Date;

@Component
public class JwtUtil {

    public final static long TOKEN_VALIDATION_SECOND = 1000L * 60 * 24 * 2;
    public final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 24 * 2;

    final static public String ACCESS_TOKEN_NAME = "accessToken";
    final static public String REFRESH_TOKEN_NAME = "refreshToken";

    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractAllClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsername(String token) {
        System.out.println("username-------------");
        System.out.println(token);
        System.out.println(extractAllClaims(token));
        return extractAllClaims(token).get("username", String.class);
    }

    public String getUserRole(String token){
        System.out.println("role---------");
        Claims claims=extractAllClaims(token);
        System.out.println(claims);
        System.out.println(claims.get("role",String.class));
        return extractAllClaims(token).get("role", String.class);
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public String generateToken(Member member) {
        return doGenerateToken(member.getUsername(),member.getRole().toString(), TOKEN_VALIDATION_SECOND);
    }

    public String generateRefreshToken(Member member) {
        return doGenerateToken(member.getUsername(),member.getRole().toString(), REFRESH_TOKEN_VALIDATION_SECOND);
    }

    public String doGenerateToken(String username, String userRole, long expireTime) {

        Claims claims  =Jwts.claims();
        claims.put("username", username);
        claims.put("role",userRole);
        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
        username=extractAllClaims(jwt).get("username", String.class);
        username = getUsername(jwt);
        return jwt;
    }

    public Boolean validateToken(String token, UserDetails userDetails) throws InterruptedException {
        final String username = getUsername(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
