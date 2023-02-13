package com.fti.usdg.track.trace.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.fti.usdg.track.trace.common.Constants;
import com.fti.usdg.track.trace.security.services.UserDetailsImpl;

import io.jsonwebtoken.*;

@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${fti.app.jwtSecret}")
  private String jwtSecret;

  @Value("${fti.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  public String generateJwtToken(Authentication authentication,String UserUUID,String groupName) {

    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

    return Jwts.builder()
        .setSubject((userPrincipal.getUsername()+Constants.HASH+UserUUID+Constants.HASH+groupName))     
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String generateJwtTokenWithoutLogin(String userName,String UserUUID,String groupName) {
	    return Jwts.builder()
	        .setSubject((userName+Constants.HASH+UserUUID+Constants.HASH+groupName))     
	        .setIssuedAt(new Date())
	        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
	        .signWith(SignatureAlgorithm.HS512, jwtSecret)
	        .compact();
	  }
  public String getUserDetailsJwtToken(String token) {
	  token = token.replace("Bearer ", "");
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }
  
  public String getUserUUIDJwtToken(String token) {
	  token = token.replace("Bearer ", "");
	  String userUUId = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	  String splis [] = userUUId.split(Constants.HASH);
    return splis[1];
  }
  
  public String getGroupNameFromJwtToken(String token) {
	  token = token.replace("Bearer ", "");
	  String userUUId = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	  String splis [] = userUUId.split(Constants.HASH);
    return splis[2];
  }
  
  
  
  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }
}
