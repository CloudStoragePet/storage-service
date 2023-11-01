package org.brain.storageservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories(basePackages = "org.brain.storageservice.repository")
public class RedisConfig {

    //    @Bean
//    JedisConnectionFactory jedisConnectionFactory() {  // default redis connection
//        return new JedisConnectionFactory();
//    }
//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory() { // configure custom redis connection
//        JedisConnectionFactory jedisConFactory
//                = new JedisConnectionFactory();
//        jedisConFactory.setHostName(host);
//        jedisConFactory.setPort(port);
//        return jedisConFactory;
//    }
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory jedisConnectionFactory) { // for custom repository
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer()); // string to byte array serializer
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new JdkSerializationRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();
        return template;
    }


}
