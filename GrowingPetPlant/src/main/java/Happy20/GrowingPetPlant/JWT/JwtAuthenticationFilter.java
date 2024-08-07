package Happy20.GrowingPetPlant.JWT;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

            // 1. Request Header에서 JWT 토큰 추출
            String accessToken = jwtProvider.resolveAccessToken((HttpServletRequest) request);
            String refreshToken = jwtProvider.resolveRefreshToken((HttpServletRequest) request);
            System.out.println("액세스 토큰: " + accessToken);
            System.out.println("리프레쉬 토큰: " + refreshToken);

            try {
                // 2. validateToken으로 토큰 유효성 검사
                if (accessToken != null && jwtProvider.validateAccessToken(accessToken)) {
                    // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext에 저장
                    Authentication authentication = jwtProvider.getAuthentication(accessToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            catch (ExpiredJwtException e) {
                // 3. 액세스 토큰이 만료된 경우 리프레쉬 토큰을 통해 액세스 토큰 재발급을 시도한다.
                if (refreshToken != null && jwtProvider.refreshTokenValidation(refreshToken, e.getClaims().getSubject())) {
                    String id = e.getClaims().getSubject();
                    String newAccessToken = jwtProvider.createAccessToken(id);
                    String newRefreshToken = jwtProvider.createRefreshToken(id);
                    HttpServletResponse httpResponse = (HttpServletResponse) response;
                    httpResponse.setHeader("AccessToken", newAccessToken);
                    httpResponse.setHeader("RefreshToken", newRefreshToken);
                    System.out.println("액세스 토큰 재발급");
                    System.out.println(newAccessToken);
                    System.out.println(newRefreshToken);
                    SecurityContextHolder.getContext().setAuthentication(jwtProvider.getAuthentication(newAccessToken));
                    }
                else {
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
                    return;
                }
            }

            chain.doFilter(request, response); // 다음 필터로 넘어가거나, 요청 처리 진행
        }
}
