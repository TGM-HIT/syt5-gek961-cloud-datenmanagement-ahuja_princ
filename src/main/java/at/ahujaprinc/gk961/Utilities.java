package at.ahujaprinc.gk961;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

/**
 * Utilities
 */
public class Utilities {

  public static String hashString(String s) {
    return Hashing.sha256()
        .hashString(s, StandardCharsets.UTF_8)
        .toString();
  }
}
