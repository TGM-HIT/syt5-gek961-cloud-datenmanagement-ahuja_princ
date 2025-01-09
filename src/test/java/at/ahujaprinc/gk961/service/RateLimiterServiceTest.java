package at.ahujaprinc.gk961.service;

import static org.junit.jupiter.api.Assertions.*;

import io.github.bucket4j.Bucket;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.Test;

class RateLimiterServiceTest {

  private final RateLimiterService rateLimiterService =
      new RateLimiterService();

  @Test
  void testRateLimiter_AllowsUnderLimit() {
    // Arrange
    String key = "user-1";

    // Act & Assert
    for (int i = 0; i < 5; i++) {
      assertTrue(rateLimiterService.use(key),
                 "Expected to allow consumption under limit");
    }
  }

  @Test
  void testRateLimiter_BlocksAboveLimit() {
    // Arrange
    String key = "user-2";

    // Act: Consume all tokens
    for (int i = 0; i < 5; i++) {
      assertTrue(rateLimiterService.use(key),
                 "Expected to allow consumption under limit");
    }

    // Assert: Block further requests
    assertFalse(rateLimiterService.use(key),
                "Expected to block consumption above limit");
  }

  @Test
  void testRateLimiter_ResetsAfterTime() throws InterruptedException {
    // Arrange
    String key = "user-3";

    // Act: Consume all tokens
    for (int i = 0; i < 5; i++) {
      assertTrue(rateLimiterService.use(key),
                 "Expected to allow consumption under limit");
    }

    assertFalse(rateLimiterService.use(key),
                "Expected to block consumption above limit");

    // Wait for refill duration (slightly longer than 1 minute for buffer)
    Thread.sleep(Duration.ofMinutes(1).toMillis() + 100);

    // Assert: Tokens should be refilled
    assertTrue(rateLimiterService.use(key),
               "Expected to allow consumption after refill");
  }

  @Test
  void testRateLimiter_HandlesMultipleKeysIndependently() {
    // Arrange
    String key1 = "user-4";
    String key2 = "user-5";

    // Act & Assert: Each key should operate independently
    for (int i = 0; i < 5; i++) {
      assertTrue(rateLimiterService.use(key1),
                 "Expected to allow consumption for key1");
      assertTrue(rateLimiterService.use(key2),
                 "Expected to allow consumption for key2");
    }

    assertFalse(rateLimiterService.use(key1),
                "Expected to block consumption for key1 above limit");
    assertFalse(rateLimiterService.use(key2),
                "Expected to block consumption for key2 above limit");
  }

  @Test
  void testRateLimiter_DefendsAgainstHighConcurrency()
      throws InterruptedException {
    // Arrange
    String key = "user-6";

    // Simulate high-concurrency environment
    Thread[] threads = new Thread[10];
    int[] allowedCount = {0};

    for (int i = 0; i < 10; i++) {
      threads[i] = new Thread(() -> {
        if (rateLimiterService.use(key)) {
          synchronized (allowedCount) { allowedCount[0]++; }
        }
      });
    }

    // Act
    for (Thread thread : threads) {
      thread.start();
    }

    for (Thread thread : threads) {
      thread.join();
    }

    // Assert
    assertEquals(5, allowedCount[0],
                 "Expected only 5 requests to be allowed under limit");
  }
}
