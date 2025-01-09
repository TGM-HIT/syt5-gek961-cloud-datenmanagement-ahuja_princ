package at.ahujaprinc.gk961;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.security.SecureRandom;

/**
 * Utilities
 */
public class Utilities {
  public static String hashString(String s) {
    return BCrypt.with(new SecureRandom()).hashToString(12, s.toCharArray());
  }

  public static boolean checkHash(String s, String hash) {
    return BCrypt.verifyer().verify(s.toCharArray(), hash).verified;
  }
}
