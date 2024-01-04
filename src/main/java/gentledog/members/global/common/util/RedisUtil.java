package gentledog.members.global.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisUtil {
    private final StringRedisTemplate template;

    // 값 가져오기
    public String getData(String key) {
        ValueOperations<String, String> valueOperations = template.opsForValue();
        return valueOperations.get(key);
    }

    // 값 존재 여부
    public boolean existData(String key) {
        return Boolean.TRUE.equals(template.hasKey(key));
    }

    // 값 저장 및 만료 시간 설정
    public void createDataExpire(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = template.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    // 값 삭제
    public void deleteData(String key) {
        template.delete(key);
    }
}
