package com.example.online_shop_back.security;


import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
//@Slf4j
public class JwtTokenProvider {
    private static final String secretKey = "MaxfiySuzG8HeckKimgaAytma";

    public String generateToken(UUID userId) {
        Date date = new Date();
        long tokenExpireTime = 604800000L;
        long l = date.getTime() + tokenExpireTime;
        Date expireDate = new Date(l);

        return Jwts
                .builder()
                .setSubject(userId.toString())
                .setIssuedAt(date)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.err.println("Muddati o'tgan");
        } catch (MalformedJwtException malformedJwtException) {
            System.err.println("Buzilgan token");
        } catch (SignatureException s) {
            System.err.println("Kalit so'z xato");
        } catch (UnsupportedJwtException unsupportedJwtException) {
            System.err.println("Qo'llanilmagan token");
        } catch (IllegalArgumentException ex) {
            System.err.println("Bo'sh token");
        }
        return false;
    }

    public String getUserIdFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
//
//    @Value("${app.jwt.access.token.key}")
//    private String JWT_SECRET_KEY_FOR_ACCESS_TOKEN;
//    @Value("${app.jwt.access.token.expiration-in-ms}")
//    private Long JWT_EXPIRED_TIME_FOR_ACCESS_TOKEN;
//
//    @Value("${app.jwt.refresh.token.key}")
//    private String JWT_SECRET_KEY_FOR_REFRESH_TOKEN;
//    @Value("${app.jwt.refresh.token.expiration-in-ms}")
//    private Long JWT_EXPIRED_TIME_FOR_REFRESH_TOKEN;
//
//    public String generateAccessToken(User user, Timestamp issuedAt) {
//        Timestamp expireDate = new Timestamp(System.currentTimeMillis() + JWT_EXPIRED_TIME_FOR_ACCESS_TOKEN);
//        String userId = String.valueOf(user.getId());
//        String generateUuid = hideUserId(userId);
//        return Jwts.builder()
//                .setSubject(generateUuid)
//                .setIssuedAt(issuedAt)
//                .setExpiration(expireDate)
//                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY_FOR_ACCESS_TOKEN)
//                .compact();
//    }
//
//    public String generateRefreshToken(User user) {
//
//        Timestamp issuedAt = new Timestamp(System.currentTimeMillis());
//        Timestamp expireDate = new Timestamp(System.currentTimeMillis() + JWT_EXPIRED_TIME_FOR_REFRESH_TOKEN);
//
//        return Jwts.builder()
//                .setSubject(user.getId().toString())
//                .setIssuedAt(issuedAt)
//                .setExpiration(expireDate)
//                .signWith(SignatureAlgorithm.HS512, JWT_SECRET_KEY_FOR_REFRESH_TOKEN)
//                .compact();
//    }
//
//    public boolean validToken(String token, boolean accessToken) {
//        try {
//            Jwts.parser().setSigningKey(accessToken ? JWT_SECRET_KEY_FOR_ACCESS_TOKEN : JWT_SECRET_KEY_FOR_REFRESH_TOKEN).parseClaimsJws(token);
//            return true;
//        } catch (Exception ex) {
//            return false;
//        }
//    }
//
//    public String getUserIdFromToken(String token, boolean accessToken) {
//        String userId = Jwts.parser()
//                .setSigningKey(accessToken ? JWT_SECRET_KEY_FOR_ACCESS_TOKEN : JWT_SECRET_KEY_FOR_REFRESH_TOKEN)
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//        return showUserId(userId);
//
//    }
//
//    private String hideUserId(String userId) {
//        String generatingUUID = String.valueOf(UUID.randomUUID());
//        String substring = generatingUUID.substring(0, 18);
//        String concat = substring.concat("-");
//        String concat1 = concat.concat(userId);
//        String substring1 = generatingUUID.substring(18);
//        return concat1.concat(substring1);
//    }
//
//    private String showUserId(String concat) {
//        return concat.substring(19, 55);
//    }
}
