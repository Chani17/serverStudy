package inu.appcenter.study4.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

// 입장권을 발급해주고 관리해주는
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${spring.jwt.secretKey}")
    private String secretKey;

    private Long tokenValidMilliseconds = 1000L * 60 * 60;   // 1시간 유효

    // 회원의 정보를 디비로부터 가져오는 것, 가져와서 인증을 해주는? 인증된 객체를 가져와준다?
    private final UserDetailsService userDetailsService;

    @PostConstruct  // 스프링 빈이 초기화되고 실행되는 놈
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // 인증 객체를 반환
    public Authentication getAuthetication(String jwtToken) {
        /**
         * 5. getmemberPk(jwtToken)을 호출하여 토큰에 저장되어 있는 회원의 정보를 가져온다.
         * 6. userDetailsService.loadUserByUsername(회원 id)를 호출하여 UserDetails 타입을 객체에 반환
         *      ->userDetailsService는 인터페이스 이므로 실제 호출은 MemberDetailsService의 loadUserByUsername() 호출
         * 10. 반환받은 UserDetails 타입의 객체를 이용하여 Authentication도 인터페이스 이므로
         *      Authentication을 구현하는 UsernamePasswordAuthenticationtoken을 생성하여 반환
         *      이때 인증 객체의 principal에는  userdetails가 들어감
         *      결국 Authentication -> principal(user타입) -> username이 회원의 정보, user객체에서 회원의 정보를 찾아낼 수 있다.
         */
        UserDetails userDetails = userDetailsService.loadUserByUsername(getMemberPk(jwtToken));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * 회원 ID를 사용하여 JWT 토큰을 발급
     * Claim는 JWT 토큰 몸통이라고 생각 -> payload라고 생각
     * claims안에는 subject, body, 발행일자, 유효시간 설정 가능
     * 시크릿 키를 사용하여 JWT토큰에 사인한다.
     */
    //입장권(JWT)을 발급
    public String createToken(String memberPk) {
        Claims claims = Jwts.claims().setSubject(memberPk); // claims = payload, 즉, 페이로드의 데이터
        Date now = new Date(); // 발급하는 시간

        return Jwts.builder()
                .setClaims(claims)      // payload를 체크하는 시간
                .setIssuedAt(now)       // 언제 발행
                .setExpiration(new Date(now.getTime() + tokenValidMilliseconds))    // 언제까지 유효
                .signWith(SignatureAlgorithm.HS256,secretKey)   // Signature: HS256 알고리즘을 통해 token이 sign이 됨
                .compact();
    }

    // memberPK = member_id를 다시 찾아옴
    // 토큰을 발행할 때 넣었던 memberPk이 나온다.

    /*
     * 6. 서버가 가지고 있는 시크릿 키를 활용해서 jwt 토큰을 파싱하고 토큰의 제목에서 회원의 id값을 추출
     */
    public String getMemberPk(String jwtToken) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken)
                .getBody().getSubject();
    }

    // JWt token 검증
    // 에버랜드 입장권 10시까지인데 내가 11시에 입장할래!
    // 에버랜드에서 너 입장유효시간 10시까지인데 왜 11시에 옴? 안댐.

    /**
     * 4. JWT 토큰의 유효성을 검사한다
     * 검사하는 내용
     *  1. secretkey를 통해서 jwt를 파싱하는데 만약 서버가 가지고 있는 secretkey로 파싱이 안된다면 변조가 되었거나 다른 서버의 jwt이므로
     *     예외가 발생
     *  2. jwt토큰의 유효시간과 서버시간을 비교해서 유효시간이 서버 시간보다 전이라면 false를 리턴
     *      -> 토큰이 만료가 되었다는 의미
     */
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);   // PAYLOAD
            return !claims.getBody().getExpiration().before(new Date());    // 지금 시간보다 유효시간이 예전인지 아닌지 검증, 토큰 시간이 유효한지 검증
        } catch (Exception e) {
            return false;
        }
    }
}
