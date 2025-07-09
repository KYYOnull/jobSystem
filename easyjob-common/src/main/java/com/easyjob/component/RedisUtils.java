package com.easyjob.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

// 2 扫描  尝试创建 RedisUtils bean 并尝试注入 RedisTemplate bean 仍没绑定保持Object
@Component("redisUtils") // 扫描后 维护 RedisUtils bean  可注入到任意类中使用
public class RedisUtils<V> {

    @Resource
    private RedisTemplate<String, V> redisTemp; // 根据类型 注入 RedisTemplate

    private static final Logger logger= LoggerFactory.getLogger(RedisUtils.class);


    public void delStorage(String[] key){ // 删除单个或多个 key

        if(null!=key && key.length>0){
            if(key.length==1){
                redisTemp.delete((key[0]));
            }else {
                redisTemp.delete(Arrays.asList(key));
            }
        }
    }

    // 缓存存取
    public V get(String key){
        return key==null? null: redisTemp.opsForValue().get(key);
    }

    public boolean set(String key,V value){
        try {
            redisTemp.opsForValue().set(key, value);
            return true;
        }catch (Exception e){
            logger.error("设置 redisKey:{}, value{} 失败", key, value);
            return false;
        }
    }

    public boolean setEx(String key, V value, long seconds){
        try{
            if(seconds> 0){
                redisTemp.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
            }else {
                set(key,value);
            }
            return true;
        }catch (Exception e){
            logger.error("设置redisKey:{}, value{} 失败", key, value);
            return false;
        }
    } // 过期key

    public long increment(String key, long delta, long seconds){ // 计数器自增，用于限流、计数

        if(delta < 0) throw new RuntimeException("递增因子必须 > 0");
        Long res = redisTemp.opsForValue().increment(key, delta);
        if(res == 1) setExpired(key, seconds);

        return  res;
    } // 计数器

    public boolean setExpired(String key, long seconds){

        try {
            if(seconds > 0) redisTemp.expire(key, seconds, TimeUnit.SECONDS);
            return true;
        } catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
    } // 命令key过期
}
