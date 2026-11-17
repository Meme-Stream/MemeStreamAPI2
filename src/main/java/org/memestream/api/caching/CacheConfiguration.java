package org.memestream.api.caching;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfiguration {

    @Bean
    public Caffeine<Object, Object> caffeine() {
        return Caffeine
                .newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS)
                .maximumSize(500);
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        var caffeineCacheManager = new CaffeineCacheManager("allMemes", "meme");
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }
}
