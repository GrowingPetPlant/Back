package Happy20.GrowingPetPlant.JWT;

import Happy20.GrowingPetPlant.User.Domain.User;
import Happy20.GrowingPetPlant.User.Service.Port.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
// JWT와 관련된 유틸 기능을 제공
// 서명에 사용한 Key 값으로 복호화를 진행하기 때문에 동일한 Key 값이 필요
public class JwtProvider {

    private static final String BEARER_TYPE = "Bearer";
    private final String PREFIX_REFRESH = "REFRESH:";
    private final String PREFIX_LOGOUT = "LOGOUT:";
    private final String PREFIX_LOGOUT_REFRESH = "LOGOUT_REFRESH:";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7일
    private final StringRedisTemplate redisTemplate;
    private final UserRepository userRepository;

    private final Key key;

    public JwtProvider(@Value("${jwt.secret}") String secretKey, StringRedisTemplate redisTemplate,
                       UserRepository userRepository) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.redisTemplate = redisTemplate;
        this.userRepository = userRepository;
    }

    public TokenDto generateToken(User loginUser) {

        // Access Token 생성
        String accessToken = createAccessToken(loginUser.getId());

        // Refresh Token 생성
        String refreshToken = createRefreshToken(loginUser.getId());

        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String createAccessToken(String id) {
        long now = (new Date()).getTime();
        User user = userRepository.findById(id);

        return Jwts.builder()
                .setSubject(user.getId()) // payload "sub": "name"
                .claim("auth", user.getAuthority())
                .setExpiration(new Date(now + ACCESS_TOKEN_EXPIRE_TIME)) // payload "exp": 151621022 (ex)
                .signWith(key, SignatureAlgorithm.HS256) // header "alg": "HS256"
                .compact();
    }

    public String createRefreshToken(String id) {
        long now = (new Date()).getTime();
        User loginMember = userRepository.findById(id);

        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // redis에 Refresh Token 저장
        redisTemplate.opsForValue()
                .set(PREFIX_REFRESH + loginMember.getId(), refreshToken, Duration.ofSeconds(REFRESH_TOKEN_EXPIRE_TIME));

        return refreshToken;
    }

    // Request Header에서 액세스 토큰 정보 추출
    public String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // Request Header에서 리프레쉬 토큰 정보 추출
    public String resolveRefreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("RefreshToken");
        if (StringUtils.hasText(refreshToken)) {
            return refreshToken;
        }
        return null;
    }

    // Jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication return
        // UserDetails: interface, User: UserDetails를 구현한 class
        UserDetails principal = new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateAccessToken(String token) {
        try {
            String id = parseClaims(token).getSubject();
            String logoutToken = redisTemplate.opsForValue().get(PREFIX_LOGOUT + id);

            System.out.println("제공된 토큰: " + token);
            System.out.println("로그아웃된 토큰: " + logoutToken);

            if (token.equals(logoutToken)) {
                System.out.println("로그아웃 처리된 액세스 토큰입니다.");
                return false;
            }

            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
            throw e;
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    // refreshToken 토큰 검증
    // db에 저장되어 있는 token과 비교
    // db에 저장한다는 것이 jwt token을 사용한다는 강점을 상쇄시킨다.
    // db 보다는 redis를 사용하는 것이 더욱 좋다. (in-memory db기 때문에 조회속도가 빠르고 주기적으로 삭제하는 기능이 기본적으로 존재합니다.)
    public boolean refreshTokenValidation(String token, String id) {
        String logoutRefresh = redisTemplate.opsForValue().get(PREFIX_LOGOUT_REFRESH + id);

        System.out.println("제공된 토큰: " + token);
        System.out.println("로그아웃된 토큰: " + logoutRefresh);

        if (token.equals(logoutRefresh)) {
            System.out.println("로그아웃 처리된 리프레쉬 토큰입니다.");
            return false;
        }

        String searchRefresh = redisTemplate.opsForValue().get(PREFIX_REFRESH + id);
        // DB에 저장한 토큰 비교
        return token.equals(searchRefresh);
    }

    // accessToken
    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public boolean confirmLogout(String accessToken, String id) {
        String logoutToken = redisTemplate.opsForValue().get(PREFIX_LOGOUT + id);
        return accessToken.equals(logoutToken);
    }
}
