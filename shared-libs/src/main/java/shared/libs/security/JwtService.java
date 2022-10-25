package shared.libs.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.bootstrap.BootstrapConfiguration;
import org.springframework.cloud.bootstrap.config.BootstrapPropertySource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import shared.libs.utils.DateUtil;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@Component
@RequiredArgsConstructor
public class JwtService {
    private static JwtService jwtService;

    private final Environment environment;

//    public static JwtService getInstance(){
//        if (jwtService != null){
//            return jwtService;
//        }
//
//        jwtService = new JwtService();
//        return jwtService;
//    }

    @Value("${spring.security.key}")
    private String key;

    public String generateToken(String user_id){
        return Jwts.builder()
                .claim("sub", user_id)
                .claim("exp", DateUtil.expirationTimeToken())
                .claim("iat", new Date())
                .signWith(SignatureAlgorithm.HS256, environment.getProperty("spring.security.key"))
                .compact();
    }

    public Object getClaim(String token, String name){
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .get(name);
    }

    public boolean validateToken(String token){
        Claims claim = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        return claim.getExpiration().after(new Date()) && claim.getSubject() != null;
    }
}
