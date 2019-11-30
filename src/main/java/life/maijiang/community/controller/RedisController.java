package life.maijiang.community.controller;

import life.maijiang.community.dto.RedisTestUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/redis")
public class RedisController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/get/{username}")
    public Object get(@PathVariable String username) {
        return redisTemplate.opsForValue().get(username);
    }

    @PutMapping("/put")
    public void put(String username, String nickname) {
        RedisTestUser user = new RedisTestUser(username, nickname);
        redisTemplate.opsForValue().set(username, user);
    }
}
