package com.gtja.user.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gtja.user.pojo.Member;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import javax.annotation.Resource;

@Configuration
@EnableAutoConfiguration
public class RedisConfig extends CachingConfigurerSupport {

    @Resource
    private RedisProperties redisProperties;

    @Bean(name = "MemberRedisTemplate")
    public RedisTemplate<String, Member> getMemberRedisTemplate(RedisConnectionFactory factory) {
//        RedisConnectionFactory factory

        RedisTemplate<String,Member> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        Jackson2JsonRedisSerializer<Member> jacksonSerial = new Jackson2JsonRedisSerializer<>(Member.class);
        ObjectMapper om = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        // 值采用json序列化
        redisTemplate.setValueSerializer(jacksonSerial);
        //采用GenericToStringSerializer
        redisTemplate.setKeySerializer(new GenericToStringSerializer<>(String.class));
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

//    @Bean(name = "MemberRedisTemplate")
//    public RedisTemplate<String, Member> getLettuceMemberRedisTemplate(LettuceConnectionFactory factory) {
////        RedisConnectionFactory factory
//
//        RedisTemplate<String,Member> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(factory);
//
//        Jackson2JsonRedisSerializer<Member> jacksonSerial = new Jackson2JsonRedisSerializer<>(Member.class);
//        ObjectMapper om = new ObjectMapper();
//        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//
//        // 值采用json序列化
//        redisTemplate.setValueSerializer(jacksonSerial);
//        //采用GenericToStringSerializer
//        redisTemplate.setKeySerializer(new GenericToStringSerializer<>(String.class));
//        redisTemplate.afterPropertiesSet();
//
//        return redisTemplate;
//    }

}
