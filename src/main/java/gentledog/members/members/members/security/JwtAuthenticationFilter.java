package gentledog.members.members.members.security;

import gentledog.members.members.members.security.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // 필터 과정
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
            @NonNull
            HttpServletRequest request,
            @NonNull
            HttpServletResponse response,
            @NonNull
            FilterChain filterChain
    ) throws ServletException, IOException {
        // HTTP 요청 헤더에서 "Authorization" 값을 가져옵니다.
        final String authHeader = request.getHeader("Authorization");
        // JWT 토큰 및 사용자 ID를 초기화합니다.
        final String jwt;
        final String membersEmail;
        // 헤더가 null이거나, "Bearer"로 시작하지 않는다면 토큰 인증을 진행하지 않음
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7); // "Bearer " 제외
        // JWT 토큰에서 사용자 email을 가져옵니다.
        membersEmail = jwtTokenProvider.getMembersEmail(jwt);
        // 유효성 검사
        if (membersEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // membersEmail 기반으로 사용자 정보를 가져옵니다.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(membersEmail);
            // 토큰이 유효한 경우 인증 정보를 생성하고 Security Context에 설정합니다.
                if (jwtTokenProvider.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    // 현재 요청에 대한 세부 정보를 인증 토큰에 설정합니다.
                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    // 인증 토큰을 Security Context에 설정합니다.
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
        }
        // 다음 필터로 요청과 응답을 전달합니다.
        filterChain.doFilter(request,response);
    }
}