package at.ahujaprinc.gk961.service;

import io.github.bucket4j.Bucket;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {
  // Rate limiting bucket per IP or token
  private ConcurrentHashMap<String, Bucket> rateLimiter =
      new ConcurrentHashMap<>();

  private Bucket resolveBucket(String key) {
    return rateLimiter.computeIfAbsent(
        key,
        k
        -> Bucket.builder()
               .addLimit(
                   limit
                   -> limit.capacity(5).refillGreedy(5, Duration.ofMinutes(1)))
               .build());
  };

  public boolean use(String key) { return resolveBucket(key).tryConsume(1); }
}
