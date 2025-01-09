package at.ahujaprinc.gk961;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtilitiesTest {

  @Test
  void testHashString_ProducesValidHash() {
    // Arrange
    String input = "securePassword123";

    // Act
    String hash = Utilities.hashString(input);

    // Assert
    assertNotNull(hash, "Hash should not be null");
    assertFalse(hash.isEmpty(), "Hash should not be empty");
  }

  @Test
  void testCheckHash_ReturnsTrueForValidHash() {
    // Arrange
    String input = "securePassword123";
    String hash = Utilities.hashString(input);

    // Act
    boolean isValid = Utilities.checkHash(input, hash);

    // Assert
    assertTrue(isValid,
               "Hash verification should return true for correct input");
  }

  @Test
  void testCheckHash_ReturnsFalseForInvalidHash() {
    // Arrange
    String input = "securePassword123";
    String wrongInput = "wrongPassword456";
    String hash = Utilities.hashString(input);

    // Act
    boolean isValid = Utilities.checkHash(wrongInput, hash);

    // Assert
    assertFalse(isValid,
                "Hash verification should return false for incorrect input");
  }

  @Test
  void testHashString_IsUniqueForSameInput() {
    // Arrange
    String input = "securePassword123";

    // Act
    String hash1 = Utilities.hashString(input);
    String hash2 = Utilities.hashString(input);

    // Assert
    assertNotEquals(
        hash1, hash2,
        "Hashes for the same input should be unique due to salting");
  }

  @Test
  void testHashString_HandlesEmptyInputGracefully() {
    // Arrange
    String input = "";

    // Act
    String hash = Utilities.hashString(input);

    // Assert
    assertNotNull(hash, "Hash should not be null even for empty input");
    assertFalse(hash.isEmpty(),
                "Hash should not be empty even for empty input");
  }
}
