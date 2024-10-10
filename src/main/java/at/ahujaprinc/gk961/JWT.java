package at.ahujaprinc.gk961;

import at.ahujaprinc.gk961.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

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
    claims.put("roles", user.getUsername());

    return Jwts.builder()
        .setSubject(user.getUsername()) // Username as the subject
        .setClaims(claims)
        .setIssuedAt(new Date()) // Issued time
        .setExpiration(
            new Date(System.currentTimeMillis() + duration)) // Expiration time
        .signWith(SECRET) // Signing the token
        .compact();
  }

  // Validate JWT token and retrieve claims
  public static Claims validateToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(SECRET)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  // Check if the token has expired
  public static boolean isTokenExpired(String token) {
    return validateToken(token).getExpiration().before(new Date());
  }

  private static Optional<Claims> extractAllClaims(String token) {
    try {
      return Optional.of(Jwts.parserBuilder()
                             .setSigningKey(SECRET)
                             .build()
                             .parseClaimsJws(token)
                             .getBody());
    } catch (ExpiredJwtException e) {
      System.err.println("JWT token expired");
      return Optional.empty();
    }
  }

  public static <T> T extractClaim(String token,
                                   Function<Claims, T> claimsResolver) {
    final Optional<Claims> claims = extractAllClaims(token);
    return claims.map(claimsResolver).orElse(null);
  }
}
