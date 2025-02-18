package at.ahujaprinc.gk961;

import at.ahujaprinc.gk961.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT
 */
public class JWT {

  private static final Key SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);
  private static final long duration = 1000 * 60 * 30; // ms

  // Generate JWT token
  public static String generateToken(User user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("name", user.getName());

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(user.getUsername()) // Username as the subject
        .setIssuedAt(new Date())        // Issued time
        .setExpiration(
            new Date(System.currentTimeMillis() + duration)) // Expiration time
        .signWith(SECRET) // Signing the token
        .compact();
  }

  public static boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(SECRET)
          .build()
          .parseClaimsJws(token)
          .getBody();
      return true;
    } catch (ExpiredJwtException e) {
      System.err.println("JWT token has expired: " + e.getMessage());
    } catch (MalformedJwtException e) {
      // Invalid token structure
      System.err.println("Invalid JWT token: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      // Token is null or empty
      System.err.println("JWT token is empty: " + e.getMessage());
    } catch (JwtException e) {
      System.err.println(e);
    }
    return false;
  }

  private static Claims extractAllClaims(String token) {
    if (validateToken(token))
      return Jwts.parserBuilder()
          .setSigningKey(SECRET)
          .build()
          .parseClaimsJws(token)
          .getBody();
    return null;
  }

  public static <T> T extractClaim(String token, String name, Class<T> type) {
    final Claims claims = extractAllClaims(token);
    final T res = claims.get(name, type);
    if (res == null)
      return null;
    return res;
  }

  public static String extractSubject(String token) {
    final Claims claims = extractAllClaims(token);
    return claims.getSubject();
  }
}
