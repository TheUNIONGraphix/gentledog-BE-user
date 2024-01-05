package gentledog.members.members.members.security;

import gentledog.members.global.common.exception.BaseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${JWT.secret-key}")
    private String secretKey;

    @Value("${JWT.token.access-expiration-time}")
    private long accessExpirationTime;

    @Value("${JWT.token.refresh-expiration-time}")
    private long refreshExpirationTime;

    /**
     * TokenProvider
     * 1. 토큰에서 loginId 가져오기
     * 2. Claims에서 원하는 claim 값 추출
     * 3. 토큰에서 모든 claims 추출
     * 4. 토큰 key 디코드
     * 5,6. 토큰 생성
     * 7. 토큰 유효성 검사
     * 8. 토큰 만료 여부 검사
     * 9. 토큰에서 만료일 추출
     */


    /**
     * 1
     * @param token
     * @return jwt토큰에서 추출한 사용자 ID 반환
     * 토큰에서 추출한 클레임에서 사용자 ID를 추출하여 반환합니다.
     */
    public String getMembersEmail(String token) throws BaseException {
        return extractClaim(token, Claims::getSubject);
    }


    /**
     * 2
     * @param token
     * @param claimsResolver jwt토큰에서 추출한 정보를 어떻게 처리할지 결정하는 함수
     * @return jwt토큰에서 모든 클레임(클레임은 토큰에 담긴 정보 의미 ) 추출한 다음 claimsResolver함수를 처리해 결과 반환
     *
     */

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /** 3
     * @param token
     * 주어진 JWT 토큰에서 모든 클레임을 추출하여 반환합니다.
     * 토큰의 서명을 확인하기 위해 사용할 서명 키(getSigningKey())를 설정하고 토큰을 파싱하여 클레임들을 추출합니다.
     */
    private Claims extractAllClaims(String token) {
        log.info("extractAllClaims token={}",token);
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token) // 이 단계에서 token의 유효성 검사 및 만료일 검사를 실시한다!
                    .getBody();
    }

    /**
     * 4
     * @param userDetails 사용자 정보
     * @return 클레임 정보와 사용자 정보를 기반으로 jwt 토큰 생성
     * 클레임 정보, 사용자 ID, 생성 시간, 만료 시간 등을 설정하고, 서명 알고리즘과 서명 키를 사용하여 토큰을 생성합니다.
     * Access Token 역활
     */

    public String generateToken(UserDetails userDetails) {

        return generateToken(Map.of(), userDetails);
    }
    public String generateToken(Map<String,Objects> extractClaims, UserDetails userDetails){
        return buildToken(extractClaims, userDetails, accessExpirationTime);
    }
    //  토큰 생성
    public String buildToken(
            Map<String, Objects> extractClaims,
            UserDetails userDetails,
            long expiration

    ) {

        log.info("generateToken {} ", userDetails);
        return Jwts.builder()
                .setClaims(extractClaims)
                .claim("role","MEMBERS")
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new java.util.Date(System.currentTimeMillis()))
                .setExpiration(new java.util.Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     *
     * @param userDetails 사용자 정보
     * @return
     * Refresh Token 역활
     */

    public String generateRefreshToken(UserDetails userDetails) {

        String refreshToken = buildToken(Map.of(), userDetails, refreshExpirationTime);
        // redis에 저장
        redisTemplate.opsForValue().set(
                userDetails.getUsername(),
                refreshToken,
                refreshExpirationTime,
                TimeUnit.MILLISECONDS
        );
        log.info("refreshExpirationTime={}",refreshExpirationTime);
        log.info("TimeUnit.MILLISECONDS={}",TimeUnit.MILLISECONDS);

        return refreshToken;
    }

    /**
     * 6
     * @param token 검증할 토큰
     * @param userDetails 사용자 정보
     * @return jwt토큰 유효성 검사
     * 토큰에서 추출한 UUID가 userDetails에서 가져온 사용자 id가 일치하며
     * 토큰이 만료되지 않은경우 토큰 유효
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        final String email = getMembersEmail(token);
        //  뽑아온 email과 받은 email가 같고 유효기간이 지나지 않았다면
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    //  만료 비교
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new java.util.Date());
    }

    //  JWT 토큰에서 만료 시간 클레임을 추출하여 반환합니다.
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //  3개로 이루어진 키값을 풀기위한 메소드
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
