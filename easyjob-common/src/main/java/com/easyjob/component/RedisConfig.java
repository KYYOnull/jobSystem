package com.easyjob.component;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

// 1 扫描时 bean方法执行 V为Object  注册为bean
@Configuration("redisConfig") // 扫描该类 解析为bean定义  通过 @Autowired / @Resource 获取 RedisTemplate bean
public class RedisConfig<V> {

    @Bean // RedisTemplate bean in springboot
    public RedisTemplate<String, V> redisTemplate(RedisConnectionFactory fact) {

        // spb 会构建 bean 依赖图。前面的bean由自动配置机制完成 创建好 RedisConnectionFactory
        RedisTemplate<String, V> temp = new RedisTemplate<>(); // 支持存储任意 Java 对象
        temp.setConnectionFactory(fact);

        temp.setKeySerializer(RedisSerializer.string());
        temp.setValueSerializer(RedisSerializer.json());
        temp.setHashKeySerializer(RedisSerializer.string());
        temp.setHashValueSerializer(RedisSerializer.json());

        temp.afterPropertiesSet();
        return temp;
    }
}
