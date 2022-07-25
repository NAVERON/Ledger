package utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import configuration.ConstantConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import model.user.RoleType;
import model.user.UserAndPermissionDTO;

/**
 * Json web Token 工具箱 
 * @author eron 
 * source : https://github.com/GabrielBB/jwt-java-utility 
 * usage : https://github.com/jwtk/jjwt 
 */
public class JWTUtils {
    
    private static final Logger log = LoggerFactory.getLogger(JWTUtils.class);
    
    private static final byte[] signatureKeyBytes = 
            Encoders.BASE64.encode(ConstantConfig.JWT_SIGNATURE_KEY.getBytes()).getBytes();
    
    public static String getToken(UserAndPermissionDTO user) {
        Date now = new Date();
        log.info("getToken now == {}", now.toString());
        
        return Jwts.builder()
                .setSubject(ConstantConfig.JWT_SUBJECT) // 主题 
                .setId(user.getId().toString())
                .setAudience(user.getUserName())
                .signWith(Keys.hmacShaKeyFor(JWTUtils.signatureKeyBytes))
                .claim("email", user.getEmailAddress()) // all of that make String Type 
                .claim("phone", user.getPhoneNumber())
                .claim("role", user.getRoleType().name())
                .claim("permission", user.getPermissionsString())
                // .setPayload("")  // plain text 
                .setIssuer(ConstantConfig.JWT_ISSUER)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + 3600_000))
                .compact();
    }

    // 从token中还原user 
    public static UserAndPermissionDTO getUser(String token) {
        /**
         *  parsePlaintextJwt 载荷为文本（不是Json），未签名
            parseClaimsJwt 载荷为claims（即Json），未签名
            parsePlaintextJws 载荷为文本（不是Json），已签名
            parseClaimsJws 载荷为claims（即Json），已签名
            
         */
        JwtParser jwtParser = null;
        Jwt<JwsHeader, Claims> jws = null;
        Claims claims = null;
        try {
            jwtParser = Jwts.parserBuilder()
                    .requireSubject(ConstantConfig.JWT_SUBJECT)
                    .requireIssuer(ConstantConfig.JWT_ISSUER)
                    .setSigningKey(Keys.hmacShaKeyFor(JWTUtils.signatureKeyBytes))
                    .build();
            jws = jwtParser.parseClaimsJws(token);
            claims = jws.getBody();
        }catch (MissingClaimException  mce) {
            log.error("MissingClaimException --> {}", mce.fillInStackTrace());
            // throw new MissingClaimException(jwt.getHeader(), claims, "MissingClaimException");
            return null;
        }catch (SignatureException se) {
            log.error("SignatureException --> {}", se.getMessage());
            // throw new SignatureException(se.toString());
            return null;
        }catch (IncorrectClaimException  ice) {
            log.error("IncorrectClaimException --> {}", ice.getCause());
            // throw new IncorrectClaimException(jwt.getHeader(), claims, "IncorrectClaimException");
            return null;
        }catch (Exception e) {
            e.printStackTrace();
            log.error("Exception --> {}", e.toString());
            return null;
        }
        
        UserAndPermissionDTO user = UserAndPermissionDTO.createBuilder()
                .id(Long.parseLong(claims.getId()))
                .userName(claims.getAudience())
                .emailAddress(claims.get("email").toString())
                .phoneNumber(claims.get("phone").toString())
                .roleType(RoleType.Of(claims.get("role").toString()))
                .permissionString(claims.get("permission").toString())
                .build();
        
        return user;
    }
    
    
}









